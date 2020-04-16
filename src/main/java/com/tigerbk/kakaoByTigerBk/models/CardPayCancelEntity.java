package com.tigerbk.kakaoByTigerBk.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tigerbk.kakaoByTigerBk.common.StateEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CardPayCancelEntity")
//카드취소내역 테이블
public class CardPayCancelEntity {

	@Id
	@NonNull
	@Column(name = "cancelKey", length = 20)
	private Long cancelKey; // 20자리

	@NonNull
	@Column(name = "approvedKey",  length = 20)
	private Long approvedKey; // 20자리
	
	
	@Column(name = "regUserId", length = 100, nullable = false)
	private String regUserId;

	// 카드 취소 상태(2자리)
	@Column(name = "cancelState", length = 2, nullable = false)
	private StateEnum cancelState;

	// 전체/부분 취소 구분자
	@Column(name = "cancelType", length = 2, nullable = false)
	private String cancelType;

	// 10억원이하 숫자
	@Column(name = "cardAmount", length = 10, nullable = false)
	private Long cardAmount;

	// 부가가치세
	@Column(name = "cardVat", length = 10, nullable = false)
	private Long cardVat;

	// 거래 시각
	@CreationTimestamp
	@Column(name = "createdTimeAt", length = 8, nullable = false, updatable = false)
	private LocalDateTime createdTimeAt;

	// 수정 시각
	@UpdateTimestamp
	@Column(name = "updateTimeAt", length = 10, nullable = false, updatable = false)
	private LocalDateTime updateTimeAt;
}
