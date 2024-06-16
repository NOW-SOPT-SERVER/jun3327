package sopt.week2clone.service.dto;

import sopt.week2clone.domain.Selling;

public record SellingSearchByLocationDto(
        Long id,
        String title,
        double price,
        String location,
        int likeCount,
        long createdPeriod
) {

    public static SellingSearchByLocationDto of(Selling selling, int likeCount, long createdPeriod) {
        return new SellingSearchByLocationDto(selling.getId(), selling.getTitle(), selling.getPrice(),
                selling.getLocation(), likeCount, createdPeriod);
    }
}
