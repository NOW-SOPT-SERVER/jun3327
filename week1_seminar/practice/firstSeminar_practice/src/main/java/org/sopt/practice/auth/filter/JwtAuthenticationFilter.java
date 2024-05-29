package org.sopt.practice.auth.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
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
                if(jwtTokenProvider.isAccessToken(token)) {
                    //access token인 경우
                    handleAccessToken(request, token);
                } else {
                    //refresh token인 경우
                    handleRefreshToken(response, token);
                    return;
                }
            } else {
                //토큰이 유효하지 않은 경우
                handleExceptionType(validationType);
            }
        } catch (UnauthorizedException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception e) {
            request.setAttribute("exception", new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION));
        }

        filterChain.doFilter(request, response);
    }

    // access token의 경우 authentication 생성
    private void handleAccessToken(HttpServletRequest request, String token) {
        Long memberId = jwtTokenProvider.getUserFromJwt(token);
        setUserAuthentication(request, memberId);
    }

    //refresh token일 경우 access token 재발 급 시도 및 응답 반환
    private void handleRefreshToken(HttpServletResponse response, String refreshToken) throws IOException {
        Long memberId = jwtTokenProvider.getUserFromJwt(refreshToken);
        String reIssuedAccessToken = jwtTokenProvider.reIssueAccessToken(refreshToken, memberId);
        response.getWriter().write(objectMapper.writeValueAsString(
                            SuccessStatusResponse.of(
                                    SuccessMessage.ACCESS_TOKEN_REISSUE_SUCCESS, AccessTokenRefreshResponse.of(reIssuedAccessToken))));
    }

    //Jwt토큰이 유효하지 않은 경우 각 validationType마다 예외처리.
    //여기서는 access Token과 refresh Token 각각에 대한 기간 만료만 커스텀했음.
    private void handleExceptionType(JwtValidationType validationType) {
        // access token의 기간 만료에 대한 예외처리
        if(validationType == EXPIRED_JWT_ACCESS_TOKEN) {
            throw new UnauthorizedException(ErrorMessage.JWT_ACCESS_TOKEN_EXPIRATION_EXCEPTION);

        // refresh token의 기간 만료에 대한 예외처리
        } else if (validationType == EXPIRED_JWT_REFRESH_TOKEN) {
            throw new UnauthorizedException(ErrorMessage.JWT_REFRESH_TOKEN_EXPIRATION_EXCEPTION);
        }
    }

    //authentication 객체 생성
    private void setUserAuthentication(HttpServletRequest request, Long memberId) {
        UserAuthentication authentication = UserAuthentication.createUserAuthentication(memberId);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}