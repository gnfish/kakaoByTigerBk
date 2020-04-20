package com.tigerbk.kakaoByTigerBk;

import com.tigerbk.kakaoByTigerBk.cardApproved.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.cardApproved.service.CardApprovedService;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.lang.Thread.sleep;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SynchronizedTest {
    @Autowired
    private CardPayApprovedRepository cardPayApprovedRepository;

    @Autowired
    CardApprovedService cardapprovedservice;

    @BeforeAll
    public static void init() {
        System.out.println("init");
    }

    @Test
    public void testCase01() throws Exception {
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

        for (int i = 0; i < 5; i++) {
            new Thread(() -> cardapprovedservice.procCardApprove(requestDto)).start();
        }

        sleep(5000);

        //when
        List<CardPayApprovedEntity> all = cardPayApprovedRepository.findAll();
        System.out.println(">>>>>>>>>>>>>> " + all.size());
    }
}