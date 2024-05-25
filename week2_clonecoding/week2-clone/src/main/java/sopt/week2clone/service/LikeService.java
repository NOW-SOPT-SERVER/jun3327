package sopt.week2clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.week2clone.domain.Like;
import sopt.week2clone.domain.Member;
import sopt.week2clone.domain.Selling;
import sopt.week2clone.repository.LikeRepository;
import sopt.week2clone.repository.MemberRepository;
import sopt.week2clone.repository.SellingRepository;
import sopt.week2clone.service.dto.CreateLikeDto;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final SellingRepository sellingRepository;

    public int getSellingLikeCount(Long sellingId) {
        return likeRepository.findSellingLikeCount(sellingId);
    }

    @Transactional
    public String createSellingLike(CreateLikeDto createLikeDto) {
        Selling findSelling = sellingRepository.findById(createLikeDto.sellingId()).orElseThrow(RuntimeException::new);
        Member findMember = memberRepository.findById(createLikeDto.memberId()).orElseThrow(RuntimeException::new);
        return likeRepository.save(Like.createOne(findSelling, findMember)).getId().toString();
    }
}
