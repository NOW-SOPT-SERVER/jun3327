package sopt.week2clone.service.dto;

public record SellingCreateDto(
        Long memberId,
        String title,
        String method,
        boolean priceProposal,
        double price,
        String text,
        String location
) {
}
