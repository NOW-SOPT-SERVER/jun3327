package org.sopt.practice.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.common.jwt.JwtValidationType;
import org.sopt.practice.domain.SuccessMessage;
import org.sopt.practice.exception.UnauthorizedException;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.service.dto.AccessTokenRefreshResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.sopt.practice.common.jwt.JwtValidationType.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {

        try {
            final String token = getJwtFromRequest(request);
            JwtValidationType validationType = jwtTokenProvider.validateToken(token);
            //토큰의 인증이 완료된 후
            if (validationType == VALID_JWT) {
                Long memberId = jwtTokenProvider.getUserFromJwt(token);

                //refersh token인 경우 access token 재발급 시도 후 응답 반환
                if(!jwtTokenProvider.isAccessToken(token)) {
                    String accessToken = jwtTokenProvider.reIssueAccessToken(token, memberId);
                    response.getWriter().write(objectMapper.writeValueAsString(
                            SuccessStatusResponse.of(SuccessMessage.ACCESS_TOKEN_REISSUE_SUCCESS, AccessTokenRefreshResponse.of(accessToken))));
                    return;
                }

                //authentication 객체 생성 과정
                UserAuthentication authentication = UserAuthentication.createUserAuthentication(memberId);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            // access token의 기간 만료에 대한 예외처리
            } else if(validationType == EXPIRED_JWT_ACCESS_TOKEN) {
                throw new UnauthorizedException(ErrorMessage.JWT_ACCESS_TOKEN_EXPIRATION_EXCEPTION);

            // refresh token의 기간 만료에 대한 예외처리
            } else if (validationType == EXPIRED_JWT_REFRESH_TOKEN) {
                throw new UnauthorizedException(ErrorMessage.JWT_REFRESH_TOKEN_EXPIRATION_EXCEPTION);
            }
        } catch (UnauthorizedException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception e) {
            request.setAttribute("exception", new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION));
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
