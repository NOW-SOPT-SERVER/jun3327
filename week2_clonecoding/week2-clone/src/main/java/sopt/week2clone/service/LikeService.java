package sopt.week2clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.week2clone.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public int getSellingLikeCount(Long sellingId) {
        likeRepository.
    }
}
