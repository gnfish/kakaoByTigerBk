package com.tigerbk.kakaoByTigerBk.services;

import java.util.HashMap;
import java.util.List;

import com.tigerbk.kakaoByTigerBk.entity.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.entity.CardPayCancelEntity;

public interface CardService {
	
	// 1. 카드 승인요청 처리
	public CardPayApprovedEntity  procCardApprove(CardPayApprovedEntity cardPayApprovedEntity);
	// 2. 카드 취소요청 처리
	public CardPayCancelEntity  procCardCancel(CardPayCancelEntity cardPayCancelEntity);
	// 3. 카드 승인 내역 조회 서비스 
	public CardPayApprovedEntity  searchCardApprove(Long uID);
	// 4. 카드 취소 내역 조회 서비스 
	public CardPayCancelEntity  searchCardCancel(Long uID);
	
}
