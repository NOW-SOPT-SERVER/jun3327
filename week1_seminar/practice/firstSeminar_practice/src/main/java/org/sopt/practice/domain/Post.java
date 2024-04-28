package org.sopt.practice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.practice.service.dto.PostCreateDto;

import java.sql.Blob;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;

    private Post(String title, String content, Blog blog) {
        this.title = title;
        this.content = content;
        this.blog = blog;
    }

    public static Post create(PostCreateDto createDto, Blog blog) {
        return new Post(createDto.title(), createDto.content(), blog);
    }
}
