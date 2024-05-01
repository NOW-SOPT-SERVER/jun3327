package sopt.week2clone.service.dto;

import sopt.week2clone.domain.Selling;

public record SellingDto(
        Long id,
        String title,
        double price,
        String location,
        int likeCount,
        long createdPeriod
) {

    public static SellingDto of(Selling selling, int likeCount, long createdPeriod) {
        return new SellingDto(selling.getId(), selling.getTitle(), selling.getPrice(),
                selling.getLocation(), likeCount, createdPeriod);
    }
}
