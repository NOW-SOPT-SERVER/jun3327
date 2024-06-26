package org.sopt.practice.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.domain.SuccessMessage;
import org.sopt.practice.service.PostService;
import org.sopt.practice.service.dto.PostCreateDto;
import org.sopt.practice.service.dto.PostFindDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<SuccessStatusResponse> createPost(@RequestHeader Long blogId,
                                                            @RequestHeader Long memberId,
                                                            @RequestBody @Valid PostCreateDto postCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).header(
                        "Location",
                        postService.create(blogId, memberId, postCreateDto))
                .body(SuccessStatusResponse.of(SuccessMessage.POST_CREATE_SUCCESS));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<SuccessStatusResponse<PostFindDto>> findPostById(@PathVariable Long postId) {

        return ResponseEntity.ok().body(SuccessStatusResponse.of(SuccessMessage.POST_FIND_SUCCESS,
                postService.findById(postId)));
    }
}
