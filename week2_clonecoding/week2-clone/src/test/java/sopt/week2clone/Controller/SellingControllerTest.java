package sopt.week2clone.Controller;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sopt.week2clone.domain.Member;
import sopt.week2clone.domain.Selling;
import sopt.week2clone.repository.MemberRepository;
import sopt.week2clone.repository.SellingRepository;
import sopt.week2clone.service.dto.CreateMemberDto;
import sopt.week2clone.settings.ApiTest;

public class SellingControllerTest extends ApiTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SellingRepository sellingRepository;

    @Nested
    public class getSellingList {

        @Test
        @DisplayName("getSellingListByLocationTest 성공")
        public void getSellingListByLocationTest() {

            //given
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

            final String requestLocation = "삼성동";

            //when
            final var response = RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .queryParam("location", requestLocation)
                    .when()
                    .get("/api/v1/home/post/sell/list")
                    .then().log().all().extract();

            //then
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

}
