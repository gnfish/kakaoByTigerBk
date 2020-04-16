package com.tigerbk.kakaoByTigerBk.cardCancel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;

public interface CardPayCancelRepository extends JpaRepository<CardPayCancelEntity, Long> {

//	CardPayCancelEntity findByuID(Long uID);
	
	
//	@Query("select sum(cardAmount), sum(cardVat) from card_pay_cancel_entity t where cancelKey=:cancelKey group by t.uID")
//	 findSumAmount(@Param("cancelKey")Long cancelKey , @Param("state")String state );	
	
}
