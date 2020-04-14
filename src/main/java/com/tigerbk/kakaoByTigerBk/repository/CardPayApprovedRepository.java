package com.tigerbk.kakaoByTigerBk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tigerbk.kakaoByTigerBk.entity.CardPayApprovedEntity;

public interface CardPayApprovedRepository extends JpaRepository<CardPayApprovedEntity, Long> {

	CardPayApprovedEntity findByuID(Long uID);

}
