package sopt.week2clone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sopt.week2clone.domain.Member;
import sopt.week2clone.domain.Selling;
import sopt.week2clone.repository.MemberRepository;
import sopt.week2clone.repository.SellingRepository;
import sopt.week2clone.service.SellingService;
import sopt.week2clone.service.dto.CreateMemberDto;
import sopt.week2clone.service.dto.SellingDto;

import java.util.List;

@SpringBootTest
public class SellingServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SellingRepository sellingRepository;

    @Autowired
    private SellingService sellingService;

    @Test
    @DisplayName("위치에 따른 판매목록 반환 성공")
    void getListByLocation() {

        Member member1 = Member.createOne(new CreateMemberDto("솝솝이"));
        memberRepository.save(member1);

        Selling selling1 = new Selling("selling1", "판매하기",
                false, 10000, "selling1 text", "삼성동", member1);
        Selling selling2 = new Selling("selling2", "판매하기",
                false, 10000, "selling2 text", "삼성동", member1);
        Selling selling3 = new Selling("selling3", "판매하기",
                false, 10000, "selling3 text", "구의동", member1);

        sellingRepository.save(selling1);
        sellingRepository.save(selling2);
        sellingRepository.save(selling3);

        List<SellingDto> list = sellingService.findListByLocation("삼성동");
        Assertions.assertEquals(list.size(), 2);
    }
}
