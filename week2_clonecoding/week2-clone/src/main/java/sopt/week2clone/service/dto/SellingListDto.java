package sopt.week2clone.service.dto;

import sopt.week2clone.domain.Selling;

public record SellingListDto(
        Long id,
        String title,
        double price,
        String location,
        int likeCount,
        int uploadTime
) {

    public static SellingListDto of(Selling selling, int likeCount, int uploadTime) {
        return new SellingListDto(selling.getId(), selling.getTitle(), selling.getPrice(),
                selling.getLocation(), likeCount, uploadTime);
    }
}
