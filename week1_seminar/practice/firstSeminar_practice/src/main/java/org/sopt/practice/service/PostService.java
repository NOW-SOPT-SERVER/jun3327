package org.sopt.practice.service;

import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.Exception.ErrorMessage;
import org.sopt.practice.Exception.UserAuthenticationException;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Post;
import org.sopt.practice.repository.PostRepository;
import org.sopt.practice.service.dto.PostCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogService blogService;

    @Transactional
    public String create(Long blogId, Long memberId, PostCreateDto postCreateDto) {

        //블로그 소유자인지 확인
        validateUser(blogId, memberId);

        Blog blog = blogService.findBlogEntityById(blogId);
        return postRepository.save(Post.create(postCreateDto, blog)).getId().toString();
    }

    private void validateUser(Long blogId, Long memberId) {
        Blog findBlog = blogService.findBlogEntityById(blogId);
        if(!(findBlog.getMember().getId().equals(memberId))) {
            throw new UserAuthenticationException(ErrorMessage.USER_AUTHENTICATE_FAIL);
        }
    }
}
