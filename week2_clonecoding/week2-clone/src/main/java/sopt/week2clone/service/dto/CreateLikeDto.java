package sopt.week2clone.service.dto;

import sopt.week2clone.domain.Selling;

public record CreateLikeDto(
        Long sellingId,
        Long memberId
) {
}
