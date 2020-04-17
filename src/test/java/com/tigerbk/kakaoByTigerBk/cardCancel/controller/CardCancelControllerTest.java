package com.tigerbk.kakaoByTigerBk.cardCancel.controller;

import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.cardCancel.repository.CardPayCancelRepository;

import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.common.UtilsLib;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardCancelControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CardPayCancelRepository cardpaycancelrepository;

    /**
     * 결제 취소, 취소 내용 조회
     * @throws Exception
     */
    @Test
    public void cardCancelSearch() throws Exception {
        String regUserId = "tester";
        String cardNumber = "123456789012";
        String cardExpiredDate = "0401";
        String cardCvc = "999";
        String cardPeriod = "00";
        Long cardAmount = 11000L;
        Long cardVat = 1000L;
        Long approvedKey = 0L;

        JSONParser jsonParssr = new JSONParser();

        ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;

        CardPayApprovedVO requestApproveDto = CardPayApprovedVO.builder()
                .regUserId(regUserId)
                .cardNumber(cardNumber)
                .cardExpiredDate(cardExpiredDate)
                .cardCvc(cardCvc)
                .cardPeriod(cardPeriod)
                .cardAmount(cardAmount)
                .cardVat(cardVat)
                .build();

        String url = "http://localhost:" + port + "/cardApprove";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestApproveDto, String.class);
        JSONObject jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        approvedKey = (Long) jsonObj.get("approvedNo");

        CardPayCancelVO requestCancelDto =  CardPayCancelVO.builder()
                .approvedKey(approvedKey)
                .cardVat(cardVat)
                .cardAmount(cardAmount)
                .regUserId(regUserId)
                .build();

        url = "http://localhost:" + port + "/cardCancel";

        //when
        responseEntity = restTemplate.postForEntity(url, requestCancelDto, String.class);
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());
        Long cancelKey = (Long) jsonObj.get("cancelKey");

        url = "http://localhost:" + port + "/searchCardCancel/" + cancelKey;

        //when
        responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(">>>>>>>>>>> " + responseEntity.getBody());
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        jsonObj = (JSONObject) jsonParssr.parse(responseEntity.getBody());
        JSONObject returnVal = (JSONObject) jsonObj.get("returnVal");

        assertThat((String) ((JSONObject) jsonObj.get("resultVO")).get("resultCode")).isEqualTo(resultCode.getCode());
        assertThat(returnVal.get("approvedKey")).isEqualTo(approvedKey);
        assertThat(returnVal.get("cancelKey")).isEqualTo(cancelKey);
    }
}
