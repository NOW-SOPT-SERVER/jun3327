package sopt.week2clone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.week2clone.service.LikeService;
import sopt.week2clone.service.dto.CreateLikeDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home/post/sell")
public class SellingLikeController {

    private final LikeService likeService;

    @PostMapping("/{sellingId}/like")
    public ResponseEntity doLike(@RequestHeader(value = "memberId") Long memberId,
                                 @PathVariable("sellingId") Long sellingId) {
        return ResponseEntity.created(URI.create(likeService.createSellingLike(new CreateLikeDto(sellingId, memberId)))).build();
    }
}
