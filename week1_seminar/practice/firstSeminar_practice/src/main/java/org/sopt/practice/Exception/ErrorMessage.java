package org.sopt.practice.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum  ErrorMessage {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ID에 해당하는 사용자가 존재하지 않네요?."),
    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 블로그를 찾을 수 없습니다."),
    USER_AUTHENTICATE_FAIL(HttpStatus.UNAUTHORIZED.value(), "해당 요청에 대한 권한이 없습니다.");
    private final int status;
    private final String message;
}
