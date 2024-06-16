package org.sopt.practice.service.dto;

public record AccessTokenRefreshResponse(String accessToken) {

    public static AccessTokenRefreshResponse of(String accessToken) {
        return new AccessTokenRefreshResponse(accessToken);
    }
}
