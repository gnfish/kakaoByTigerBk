package com.tigerbk.kakaoByTigerBk.cardApproved.service;

import org.springframework.data.repository.query.Param;

import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;

public interface CardApprovedService {
	
	// 1. 카드 승인요청 처리
	public CardPayApprovedEntity  procCardApprove( CardPayApprovedVO cardpayapprovedvo);

	// 2. 카드 승인 내역 조회 서비스 
	public CardPayApprovedEntity  searchCardApproveByApprovedKey( CardPayApprovedVO cardpayapprovedvo);
	
	
	public Boolean updateCanCelStateByApprovedKey (CardPayApprovedEntity cardPayApprovedEntity);
		
	
}
