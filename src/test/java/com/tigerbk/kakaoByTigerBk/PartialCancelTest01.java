package com.tigerbk.kakaoByTigerBk;

import com.tigerbk.kakaoByTigerBk.cardApproved.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.cardCancel.repository.CardPayCancelRepository;
import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PartialCancelTest01 {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CardPayCancelRepository cardpaycancelrepository;

    @Autowired
    private CardPayApprovedRepository cardPayApprovedRepository;

    ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;

    /**
     * 결제
     * 11,000(1,000)원 결제 성공
     * @throws Exception
     */
    @Test
    public void testCase01() throws Exception {
        String regUserId = "홍길동";
        String cardNumber = "123456789012";
        String cardExpiredDate = "0401";
        String cardCvc = "999";
        String cardPeriod = "00";
        Long cardAmount;
        Long cardVat;
        Long approvedKey;

        // CASE 01
        // 결제
        // 11,000(1,000)원 결제 성공
        cardAmount = 11000L;
        cardVat = 1000L;

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
        approvedKey = (Long) jsonObj.get("approvedNo");
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // CASE 02
        // 부분취소
        // 1,100(100)원 취소 성공
        cardAmount = 1100L;
        cardVat = 100L;
        CardPayCancelVO requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // CASE 02
        // 부분취소
        // 1,100(100)원 취소 성공
        cardAmount = 3300L;
        cardVat = null;

        requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // Case 04
        // 부분취소
        // 7,000원 취소하려 했으나 남은 결제금액보다 커서 실패
        cardAmount = 7000L;
        cardVat = null;

        requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        resultCode = ErrorCodeEnum.SYSTEM_ERROR;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // Case 05
        // 부분취소
        // 6,600(700)원 취소하려 했으나 남은 부가가치세보다 취소요청 부가가치세가 커서 실패
        cardAmount = 6600L;
        cardVat = 700L;

        requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        resultCode = ErrorCodeEnum.SYSTEM_ERROR;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());


        // Case 06
        // 부분취소
        // 6,600(600)원 성공
        cardAmount = 6600L;
        cardVat = 600L;

        requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        resultCode = ErrorCodeEnum.PROC_SUCCESS;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());

        // Case 07
        // 부분취소
        // 100원 취소하려했으나 남은 결제금액이 없어서 실패
        cardAmount = 100L;
        cardVat = null;

        requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonParssr = new JSONParser();
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        resultCode = ErrorCodeEnum.SYSTEM_ERROR;
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());
    }
}
