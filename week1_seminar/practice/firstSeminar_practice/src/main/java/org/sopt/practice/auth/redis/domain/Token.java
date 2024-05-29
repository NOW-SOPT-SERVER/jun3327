package org.sopt.practice.auth.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "", timeToLive = 24 * 60 * 60 * 1000L * 14)
@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    private static Token of(
            final Long id,
            final String refreshToken
    ) {
        return Token.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
    }

}
