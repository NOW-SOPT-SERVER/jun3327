package org.sopt.practice.settings;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

//테스트에 필요한 포트를 랜덤으로 지정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    //지정한 포트를 아래 port 변수에 대입
    @LocalServerPort
    private int port;

    //모든 테스트 전에 수행
    @BeforeEach
    void setUp() {

        //인수테스트에 사용하는 RestAssured의 port를 port로 대입.
        RestAssured.port = port;
    }
}
