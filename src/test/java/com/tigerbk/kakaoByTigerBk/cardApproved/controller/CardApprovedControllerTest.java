package com.tigerbk.kakaoByTigerBk.cardApproved.controller;

import com.tigerbk.kakaoByTigerBk.cardApproved.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class) // for JUnit4
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardApprovedControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CardPayApprovedRepository cardPayApprovedRepository;

//    @AfterEach()
//    public void tearDown() throws Exception {
//        cardPayApprovedRepository.deleteAll();
//    }

    /**
     * 카드 승인
     */
    @Test
    public void CardPayApprovedEntity_cardApprove() throws Exception {
        String regUserId = "tester";
        String cardNumber = "123456789012";
        String cardExpiredDate = "0401";
        String cardCvc = "999";
        String cardPeriod = "00";
        Long cardAmount = 11000L;
        Long cardVat = 1000L;

        CardPayApprovedVO requestDto = CardPayApprovedVO.builder()
                .regUserId(regUserId)
                .cardNumber(cardNumber)
                .cardExpiredDate(cardExpiredDate)
                .cardCvc(cardCvc)
                .cardPeriod(cardPeriod)
                .cardAmount(cardAmount)
                .cardVat(cardVat)
                .build();

        String url = "http://localhost:" + port + "/cardApprove";

        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);
        JSONParser jsonParssr = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // check SUCCESS
        ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());
        assertThat((Long) jsonObj.get("approvedNo")).isGreaterThan(0L);
//        System.out.println(">>>>>>>>>>> " + responseEntity.getBody());

        // check db data
        List<CardPayApprovedEntity> all = cardPayApprovedRepository.findAll();
        assertThat(all.get(0).getRegUserId()).isEqualTo(regUserId);
        assertThat(all.get(0).getCardNumber()).isEqualTo(cardNumber);
        assertThat(all.get(0).getCardExpiredDate()).isEqualTo(cardExpiredDate);
        assertThat(all.get(0).getCardCvc()).isEqualTo(cardCvc);
        assertThat(all.get(0).getCardAmount()).isEqualTo(cardAmount);
        assertThat(all.get(0).getCardVat()).isEqualTo(cardVat);
    }

    /**
     * 카드 승인 내역 상세 조회
     * @trows Exception
     */
    @Test
    public void CardPayApprovedEntity_searchCardApprove() throws Exception {
        JSONParser jsonParssr = new JSONParser();
        ResponseEntity<String> responseEntity;

        String regUserId = "tester";
        String cardNumber = "123456789012";
        String cardExpiredDate = "0401";
        String cardCvc = "999";
        String cardPeriod = "00";
        Long cardAmount = 11000L;
        Long cardVat = 1000L;

        CardPayApprovedVO requestDto = CardPayApprovedVO.builder()
                .regUserId(regUserId)
                .cardNumber(cardNumber)
                .cardExpiredDate(cardExpiredDate)
                .cardCvc(cardCvc)
                .cardPeriod(cardPeriod)
                .cardAmount(cardAmount)
                .cardVat(cardVat)
                .build();
        String url = "http://localhost:" + port + "/cardApprove";
        responseEntity = restTemplate.postForEntity(url, requestDto, String.class);
        JSONObject jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        Long approvedNo = (Long) jsonObj.get("approvedNo"); // 카드승인번호

        url = "http://localhost:" + port + "/searchCardApprorve/" + approvedNo;

        //when
        responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(">>>>>>>>>>> " + responseEntity.getBody());
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        JSONObject returnVal = (JSONObject) jsonObj.get("returnVal");

        // check SUCCESS
        ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // check value
        assertThat(returnVal.get("approvedKey")).isEqualTo(approvedNo);
        assertThat(returnVal.get("regUserId")).isEqualTo(regUserId);
        assertThat(returnVal.get("cardNumber")).isEqualTo(cardNumber);
    }
}
