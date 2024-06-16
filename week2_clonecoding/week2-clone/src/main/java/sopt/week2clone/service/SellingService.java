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
import sopt.week2clone.service.dto.SellingSearchByLocationDto;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellingService {

    private final SellingRepository sellingRepository;
    private final MemberRepository memberRepository;
    private final LikeService likeService;
    private final S3Service s3Service;
    private static final String BLOG_S3_UPLOAD_FOLER = "blog/";

    @Transactional
    public String createSelling(SellingCreateDto sellingCreateDto) {
        Member findMember = memberRepository.findById(sellingCreateDto.memberId()).orElseThrow(
                () -> new EntityNotFoundException("해당하는 id의 사용자가 없습니다.")
        );
        try {
            String imgUrl = s3Service.uploadImage(BLOG_S3_UPLOAD_FOLER, sellingCreateDto.imgFile());
            return sellingRepository.save(Selling.create(sellingCreateDto, findMember, imgUrl)).getId().toString();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SellingSearchByLocationDto> findListByLocation(String location) {
        return sellingRepository.findByLocationContaining(location).stream().map(
                selling -> SellingSearchByLocationDto.of(selling,
                        likeService.getSellingLikeCount(selling.getId()),
                        parseCreatedAtInMinute(selling.getCreatedAt())))
                .toList();
    }

    //분 단위로 판매 등록 기간 반환
    private long parseCreatedAtInMinute(LocalDateTime createdAt) {
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        return duration.toMinutes();
    }

    @Transactional
    public void deleteById(Long sellingId) {
        Selling findSelling = sellingRepository.findById(sellingId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 상품 등록이 없음 ")
        );
        try {
            s3Service.deleteImage(findSelling.getImgUrl());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        sellingRepository.deleteById(sellingId);
    }
}
