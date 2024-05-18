package sopt.week2clone.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.week2clone.domain.Member;
import sopt.week2clone.domain.Selling;
import sopt.week2clone.repository.MemberRepository;
import sopt.week2clone.repository.SellingRepository;
import sopt.week2clone.service.dto.SellingCreateDto;
import sopt.week2clone.service.dto.SellingListDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellingService {

    private final SellingRepository sellingRepository;
    private final MemberRepository memberRepository;
    private final LikeService likeService;

    @Transactional
    public String createSelling(SellingCreateDto sellingCreateDto) {
        Member findMember = memberRepository.findById(sellingCreateDto.memberId()).orElseThrow(
                () -> new EntityNotFoundException("해당하는 id의 사용자가 없습니다.")
        );
        Selling selling = Selling.builder()
                .text(sellingCreateDto.text())
                .title(sellingCreateDto.title())
                .method(sellingCreateDto.method())
                .price(sellingCreateDto.price())
                .priceProposal(sellingCreateDto.priceProposal())
                .location(sellingCreateDto.location())
                .member(findMember)
                .build();
        sellingRepository.save(selling);

        return selling.getId().toString();
    }

    public List<SellingListDto> findListByLocation(String location) {
        sellingRepository.findByLocationContaining(location).stream().map(
                selling -> SellingListDto.of(selling,
                        likeService.getSellingLikeCount(selling.getId()),
                        selling.getCreatedAt())
        );
    }

    private


}
