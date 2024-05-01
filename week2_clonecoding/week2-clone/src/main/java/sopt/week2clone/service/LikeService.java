package sopt.week2clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.week2clone.domain.Like;
import sopt.week2clone.domain.Member;
import sopt.week2clone.domain.Selling;
import sopt.week2clone.repository.LikeRepository;
import sopt.week2clone.service.dto.CreateLikeDto;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final SellingService sellingService;

    public int getSellingLikeCount(Long sellingId) {
        return likeRepository.findSellingLikeCount(sellingId);
    }

    @Transactional
    public String create(CreateLikeDto createLikeDto) {
        Member findMember = memberService.findMemberEntityById(createLikeDto.memberId());
        Selling findSelling = sellingService.findSellingEntityById(createLikeDto.sellingId());

        return likeRepository.save(Like.createOne(findSelling, findMember)).getId().toString();
    }
}
