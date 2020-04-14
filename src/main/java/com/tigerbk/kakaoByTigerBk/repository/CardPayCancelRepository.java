package com.tigerbk.kakaoByTigerBk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tigerbk.kakaoByTigerBk.entity.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.entity.CardPayCancelEntity;

public interface CardPayCancelRepository extends JpaRepository<CardPayCancelEntity, Long> {

	CardPayCancelEntity findByuID(Long uID);
}
