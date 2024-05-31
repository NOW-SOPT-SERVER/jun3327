package sopt.week2clone.service.dto;

import org.springframework.web.multipart.MultipartFile;

public record SellingCreateDto(
        Long memberId,
        String title,
        String method,
        boolean priceProposal,
        double price,
        String text,
        String location,
        MultipartFile imgFile
) {
}
