//package sopt.week2clone.Controller;
//
//import io.restassured.RestAssured;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import sopt.week2clone.domain.Member;
//import sopt.week2clone.domain.Selling;
//import sopt.week2clone.repository.MemberRepository;
//import sopt.week2clone.repository.SellingRepository;
//import sopt.week2clone.service.dto.CreateMemberDto;
//import sopt.week2clone.service.dto.SellingCreateDto;
//import sopt.week2clone.settings.ApiTest;
//
//public class SellingControllerTest extends ApiTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private SellingRepository sellingRepository;
//
//    @Nested
//    public class getSellingList {
//
//        @Test
//        @DisplayName("getSellingListByLocationTest 성공")
//        public void getSellingListByLocationTest() {
//
//            //given
//            Member member1 = Member.createOne(new CreateMemberDto("솝솝이"));
//            memberRepository.save(member1);
//
//            SellingCreateDto sellingCreateDto1 = new SellingCreateDto(1L,"selling1", "판매하기",
//                    false, 10000, "selling1 text", "삼성동");
//
//            SellingCreateDto sellingCreateDto2 = new SellingCreateDto(1L,"selling2", "판매하기",
//                    false, 10000, "selling2 text", "삼성동");
//
//            SellingCreateDto sellingCreateDto3 = new SellingCreateDto(1L,"selling3", "판매하기",
//                    false, 10000, "selling3 text", "구의동");
//
//            Selling selling1 = Selling.create(sellingCreateDto1, member1);
//            Selling selling2 = Selling.create(sellingCreateDto2, member1);
//            Selling selling3 = Selling.create(sellingCreateDto3, member1);
//
//            sellingRepository.save(selling1);
//            sellingRepository.save(selling2);
//            sellingRepository.save(selling3);
//
//            final String requestLocation = "삼성동";
//
//            //when
//            final var response = RestAssured
//                    .given()
//                    .log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .queryParam("location", requestLocation)
//                    .when()
//                    .get("/api/v1/home/post/sell/list")
//                    .then().log().all().extract();
//
//            //then
//            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }
//
//        @Test
//        @DisplayName("판매물품에 좋아요 추가 성공")
//        public void doLikeTest() {
//            //given
//            Member member1 = Member.createOne(new CreateMemberDto("솝솝이"));
//            Member member2 = Member.createOne(new CreateMemberDto("솝솝이"));
//
//            memberRepository.save(member1);
//            memberRepository.save(member2);
//
//            SellingCreateDto sellingCreateDto1 = new SellingCreateDto(1L,"selling1", "판매하기",
//                    false, 10000, "selling1 text", "삼성동");
//
//            Selling selling1 = Selling.create(sellingCreateDto1, member1);
//
//            sellingRepository.save(selling1);
//            final String requestLocation = "삼성동";
//
//            //when
//            final var response1 = sendLikeRequest(member1.getId(), selling1.getId());
//            final var response2 = sendLikeRequest(member2.getId(), selling1.getId());
//
//            final var response3 = RestAssured
//                    .given()
//                    .log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .queryParam("location", requestLocation)
//                    .when()
//                    .get("/api/v1/home/post/sell/list")
//                    .then().log().all().extract();
//
//            //then
//            Assertions.assertThat(response1.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//            Assertions.assertThat(response2.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//            Assertions.assertThat(response3.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }
//
//        private ExtractableResponse<Response> sendLikeRequest(Long memberId, Long sellingId) {
//            return RestAssured
//                    .given()
//                    .log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .header("memberId", memberId)
//                    .pathParam("sellingId", sellingId)
//                    .when()
//                    .post("/api/v1/home/post/sell/{sellingId}/like")
//                    .then().log().all().extract();
//        }
//
//    }
//
//}
