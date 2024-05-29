package org.sopt.practice.common.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.auth.redis.domain.Token;
import org.sopt.practice.auth.redis.repository.RedisTokenRepository;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RedisTokenRepository redisTokenRepository;

    private static final String USER_ID = "userId";

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 1000L; // 10분

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000L; // 14일

    @Value("${jwt.secret}")
    private String JWT_SECRET;


    public String issueAccessToken(final Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String issueRefreshToken(final Authentication authentication) {
        String token = generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
        redisTokenRepository.save(Token.of(Long.valueOf(authentication.getPrincipal().toString()), token));
        return token;
    }

    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));      // 만료 시간
        claims.put(USER_ID, authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
                .setClaims(claims) // Claim
                .signWith(getSigningKey()) // Signature
                .compact();
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); //SecretKey 통해 서명 생성
        return Keys.hmacShaKeyFor(encodedKey.getBytes());   //일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘 사용
    }

    //토큰이 유효하지 않을 때 반환 값 설정
    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            // 요청에서 온 토큰이 Access Token, Refresh Token일 경우를 나눠서 각각 예외처리를 위한 분기점
            Claims claims = ex.getClaims();
            if(claims.getExpiration().getTime() - claims.getIssuedAt().getTime() == ACCESS_TOKEN_EXPIRATION_TIME) {
                return JwtValidationType.EXPIRED_JWT_ACCESS_TOKEN;
            } else {
                return JwtValidationType.EXPIRED_JWT_REFRESH_TOKEN;
            }
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    public boolean isAccessToken(String token) {
        Claims body = getBody(token);
        return (body.getExpiration().getTime() - body.getIssuedAt().getTime()) == ACCESS_TOKEN_EXPIRATION_TIME;
    }

    public String reIssueAccessToken(String refreshToken, Long id) {
        Token findRefreshToken = redisTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new RuntimeException("서버 에러")
        );
        if(findRefreshToken.getId().equals(id)) {
            return issueAccessToken(UserAuthentication.createUserAuthentication(id));
        } else {
            throw new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get(USER_ID).toString());
    }
}
