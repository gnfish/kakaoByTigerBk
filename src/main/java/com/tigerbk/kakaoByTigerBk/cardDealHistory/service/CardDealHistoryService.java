package com.tigerbk.kakaoByTigerBk.cardDealHistory.service;

import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;

public interface CardDealHistoryService {

	// 1. 카드 취소요청 처리
		public CardPayCancelEntity procCardCancel(CardPayCancelVO cardPayCancelVO);

		
}
