package org.sopt.practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.sopt.practice.auth.PrincipalHandler;
import org.sopt.practice.domain.SuccessMessage;
import org.sopt.practice.service.BlogService;
import org.sopt.practice.service.dto.BlogCreateRequest;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.service.dto.BlogTitleUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final PrincipalHandler principalHandler;

//    @PostMapping("/blog")
//    public ResponseEntity<SuccessStatusResponse> createBlog(
//            @RequestHeader Long memberId,
//            @RequestBody BlogCreateRequest blogCreateRequest) {
//        return ResponseEntity.status(HttpStatus.CREATED).header(
//                        "Location",
//                        blogService.create(memberId, blogCreateRequest))
//                .body(SuccessStatusResponse.of(SuccessMessage.BLOG_CREATE_SUCCESS));
//    }

//    @PostMapping("/blog")
//    public ResponseEntity createBlog(
//            BlogCreateRequest blogCreateRequest
//    ) {
//        return ResponseEntity.created(URI.create(blogService.create(
//                principalHandler.getUserIdFromPrincipal(), blogCreateRequest))).build();
//    }

    @PostMapping("/blog")
    public ResponseEntity createBlog(
            @ModelAttribute BlogCreateRequest blogCreateRequest
    ) {
        return ResponseEntity.created(URI.create(blogService.create(
                principalHandler.getUserIdFromPrincipal(), blogCreateRequest))).build();
    }

    @PatchMapping("/blog/{blogId}/title")
    public ResponseEntity updateBlogTitle(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogTitleUpdateRequest blogTitleUdpateRequest
    ) {
        blogService.updateTitle(blogId, blogTitleUdpateRequest);
        return ResponseEntity.noContent().build();
    }
}
