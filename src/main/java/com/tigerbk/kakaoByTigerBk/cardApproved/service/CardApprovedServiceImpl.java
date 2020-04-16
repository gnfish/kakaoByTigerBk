package com.tigerbk.kakaoByTigerBk.cardApproved.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tigerbk.kakaoByTigerBk.cardApproved.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.cardDealHistory.repository.CardPayDealHistoryRepository;
import com.tigerbk.kakaoByTigerBk.common.ErrorMessage;
import com.tigerbk.kakaoByTigerBk.common.StateEnum;
import com.tigerbk.kakaoByTigerBk.common.UtilsLib;
import com.tigerbk.kakaoByTigerBk.exception.RestException;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;

@Service
public class CardApprovedServiceImpl implements CardApprovedService {

	@Autowired
	CardPayApprovedRepository cardpayapprovedrepository;

	@Autowired
	CardPayDealHistoryRepository cardpaydealhistoryrepository;

	// 1. 카드 승인요청 처리
	@Override
	@Transactional()
	public CardPayApprovedEntity procCardApprove(CardPayApprovedVO cardpayapprovedvo) {
		// 1. uid 유니크키 생성
		Long approvedKey = 0L;
		approvedKey = UtilsLib.getUniqueID();
		cardpayapprovedvo.setApprovedKey(approvedKey);

		CardPayApprovedEntity cardPayApprovedEntity = new CardPayApprovedEntity();

		cardPayApprovedEntity.setApprovedKey(approvedKey);
		cardPayApprovedEntity.setRegUserId(cardpayapprovedvo.getRegUserId());
		cardPayApprovedEntity.setCardNumber(cardpayapprovedvo.getCardNumber());
		cardPayApprovedEntity.setCardExpiredDate(cardpayapprovedvo.getCardExpiredDate());
		cardPayApprovedEntity.setCardCvc(cardpayapprovedvo.getCardCvc());
		cardPayApprovedEntity.setCardAmount(cardpayapprovedvo.getCardAmount());
		cardPayApprovedEntity.setApprovedState(StateEnum.APPORVE);
		cardPayApprovedEntity.setCardPeriod(cardpayapprovedvo.getCardPeriod());
		cardPayApprovedEntity.setCardCancelAmount(0L);
		cardPayApprovedEntity.setCardCancelVat(0L);
		

		Long vatAmount = 0L;

		try {
			// 부가세가 없을 경우 계산
			if (cardpayapprovedvo.getCardVat() == 0 || cardpayapprovedvo.getCardVat() == null || Objects.isNull(cardpayapprovedvo.getCardVat())) {
				vatAmount = UtilsLib.CalcVat(cardpayapprovedvo.getCardAmount());
			} else {
				vatAmount = cardpayapprovedvo.getCardVat();
			}
			cardPayApprovedEntity.setCardVat(vatAmount);

			// 전문 조립후 외부통신내역 테이블 적재

			// 내부 카드 승인내역 테이블 적재
			CardPayApprovedEntity rData = cardpayapprovedrepository.save(cardPayApprovedEntity);

		} catch (Exception e) {
		
			throw new ErrorMessage(100, "카드승인이 실패되었습니다.");
		}

		return cardPayApprovedEntity;
	}

	// 2. 카드 승인 내역 조회 서비스
	@Override
//	@Transactional(readOnly = true)
	public CardPayApprovedEntity searchCardApproveByApprovedKey(CardPayApprovedVO cardpayapprovedvo) {
		CardPayApprovedEntity returnVo = new CardPayApprovedEntity();
		returnVo = cardpayapprovedrepository.findByApprovedKey(cardpayapprovedvo.getApprovedKey());
		// .orElseThrow( () -> new RestException(HttpStatus.NOT_FOUND, "카드 승인 내역 조회 데이타
		// 존재하지않습니다."));

		System.out.println("searchCardApprove()  =>>>>>>>> " + returnVo);

		return returnVo;
	}

	// 카드 승인 취소 처리 업데이트
	@Override
	public Boolean updateCanCelStateByApprovedKey(CardPayApprovedEntity cardPayApprovedEntity ) {
		Boolean rtn = true;
		try {
			cardpayapprovedrepository.updateCanCelStateByApprovedKey(cardPayApprovedEntity.getApprovedKey()
					, cardPayApprovedEntity.getApprovedState()
					, cardPayApprovedEntity.getCardCancelAmount()
					, cardPayApprovedEntity.getCardCancelVat()
			);
		} catch(Exception e) {
			rtn = false;
		} 


		return true;
	}

}
