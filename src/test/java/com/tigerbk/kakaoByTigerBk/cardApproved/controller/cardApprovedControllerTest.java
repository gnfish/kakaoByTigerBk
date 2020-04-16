package com.tigerbk.kakaoByTigerBk.cardApproved.controller;

import com.tigerbk.kakaoByTigerBk.cardApproved.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
//@RunWith(SpringRunner.class) // for JUnit4
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class cardApprovedControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CardPayApprovedRepository cardPayApprovedRepository;

    @AfterEach()
    public void tearDown() throws Exception {
        cardPayApprovedRepository.deleteAll();
    }

    @Test
    public void CardPayApprovedEntity_cardApprove() throws Exception {
        String regUserId = "tester";
        String cardNumber = "123456789012";
        String cardExpiredDate = "0401";
        String cardCvc = "999";
        String cardPeriod = "00";
        Long cardAmount = Long.valueOf("11000");
        Long cardVat = Long.valueOf("1000");

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
        ResponseEntity<CardPayApprovedVO> responseEntity = restTemplate.postForEntity(url, requestDto, CardPayApprovedVO.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<CardPayApprovedEntity> all = cardPayApprovedRepository.findAll();
        assertThat(all.get(0).getRegUserId()).isEqualTo(regUserId);
        assertThat(all.get(0).getCardNumber()).isEqualTo(cardNumber);
        assertThat(all.get(0).getCardExpiredDate()).isEqualTo(cardExpiredDate);
        assertThat(all.get(0).getCardCvc()).isEqualTo(cardCvc);
        assertThat(all.get(0).getCardAmount()).isEqualTo(cardAmount);
        assertThat(all.get(0).getCardVat()).isEqualTo(cardVat);
    }
}
