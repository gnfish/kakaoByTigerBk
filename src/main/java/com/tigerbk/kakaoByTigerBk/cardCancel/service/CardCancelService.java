package com.tigerbk.kakaoByTigerBk.cardCancel.service;

import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;

public interface CardCancelService {

	// 1. 카드 취소요청 처리
	public CardPayCancelEntity procCardCancel(CardPayCancelVO cardPayCancelVO);

	// 2. 카드 취소 내역 조회 서비스
	public CardPayCancelEntity searchCardCancel(CardPayCancelVO cardPayCancelVO);
}
