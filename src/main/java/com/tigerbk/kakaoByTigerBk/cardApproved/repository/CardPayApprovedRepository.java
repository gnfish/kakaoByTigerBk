package com.tigerbk.kakaoByTigerBk.cardApproved.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigerbk.kakaoByTigerBk.common.StateEnum;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;

public interface CardPayApprovedRepository extends JpaRepository<CardPayApprovedEntity, Long> {
	// UID로 검색하기
	CardPayApprovedEntity findByApprovedKey(Long approvedKey);

	CardPayApprovedEntity findByApprovedKeyAndApprovedState(Long approvedKey, StateEnum approvedState);

	
	@Modifying
	@Query(value = " UPDATE card_pay_approved_entity " 
			+ " SET approved_State = :approvedState "
			+ " ,  card_Cancel_Amount = :cardCancelAmount "
			+ " ,  card_Cancel_Vat = :cardCancelVat "
			+ " WHERE approved_Key = :approvedKey", nativeQuery = true)
	void updateCanCelStateByApprovedKey(
			@Param("approvedKey") Long approvedKey, 
			@Param("approvedState") StateEnum approvedState,
			@Param("cardCancelAmount") Long cardCancelAmount, 
			@Param("cardCancelVat") Long cardCancelVat)
			throws Exception;

}
