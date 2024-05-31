package org.sopt.practice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum  ErrorMessage {

    MEMBER_NOT_FOUND(HttpStatus.NOT_EXTENDED.value(), "ID에 해당하는 사용자가 존재하지 않네요?."),
    BLOG_NOT_FOUND(HttpStatus.NOT_EXTENDED.value(), "해당 블로그를 찾을 수 없습니다."),
    JWT_UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "사용자의 로그인 검증을 실패했습니다.");
    private final int status;
    private final String message;
}
