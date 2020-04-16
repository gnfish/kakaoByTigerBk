package com.tigerbk.kakaoByTigerBk.cardDealHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tigerbk.kakaoByTigerBk.models.CardPaySendDataEntity;

public interface CardPayDealHistoryRepository extends JpaRepository<CardPaySendDataEntity, Long>{

}
