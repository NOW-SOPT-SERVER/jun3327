package org.sopt.practice.service.dto;

import org.sopt.practice.domain.Blog;

public record PostCreateDto(
        String title,
        String content
) {

}
