package org.sopt.practice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter

public enum SuccessMessage {

    BLOG_CREATE_SUCCESS(HttpStatus.CREATED.value(),"블로그 생성 완료"),
    POST_CREATE_SUCCESS(HttpStatus.CREATED.value(), "글 작성 완료");
    private final int status;
    private final String message;
}
