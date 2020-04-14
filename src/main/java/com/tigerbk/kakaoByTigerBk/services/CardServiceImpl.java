package com.tigerbk.kakaoByTigerBk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigerbk.kakaoByTigerBk.entity.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.entity.CardPayCancelEntity;
import com.tigerbk.kakaoByTigerBk.repository.CardPayApprovedRepository;
import com.tigerbk.kakaoByTigerBk.repository.CardPayCancelRepository;
import com.tigerbk.kakaoByTigerBk.repository.CardPayDealHistoryRepository;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardPayApprovedRepository cardpayapprovedrepository;

	@Autowired
	CardPayCancelRepository cardpaycancelrepository;

	@Autowired
	CardPayDealHistoryRepository cardpaydealhistoryrepository;

	@Override
	// 1. 카드 승인요청 처리
	public CardPayApprovedEntity procCardApprove(CardPayApprovedEntity cardPayApprovedEntity) {	
		cardpayapprovedrepository.save(cardPayApprovedEntity);
		return cardPayApprovedEntity;
	}

	@Override
	// 2. 카드 취소요청 처리
	public CardPayCancelEntity procCardCancel(CardPayCancelEntity cardPayCancelEntity) {		
		cardpaycancelrepository.save(cardPayCancelEntity);
		return cardPayCancelEntity;
	}

	@Override
	// 3. 카드 승인 내역 조회 서비스
	public CardPayApprovedEntity searchCardApprove(Long uID) {
		CardPayApprovedEntity returnVo = null;
		returnVo = cardpayapprovedrepository.findByuID(uID);
		return returnVo;
	}

	@Override
	// 4. 카드 취소 내역 조회 서비스
	public CardPayCancelEntity searchCardCancel(Long uID) {	
		CardPayCancelEntity returnVo = null;
		returnVo = cardpaycancelrepository.findByuID(uID);
		return returnVo;
	}

}
