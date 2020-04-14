package com.tigerbk.kakaoByTigerBk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@Column(name = "uID")
	private Long uID; // 20자리

	@Column(name = "regUserId", length = 100, nullable = false)
	private String regUserId;

	// 카드 취소 상태(2자리)
	@Column(name = "state", length = 2, nullable = false)
	private String state;
	
	// 전체/부분 취소 구분자
	@Column(name = "cancelType", length = 2, nullable = false)
	private String cancelType;

	// 10억원이하 숫자
	@Column(name = "cardAmount", length = 10, nullable = false)
	private Long cardAmount;

	// 부가가치세
	@Column(name = "cardVat", length = 10, nullable = false)
	private Long cardVat;

	// 거래일자 (20200405)
	@Column(name = "regDate", length = 8, nullable = false)
	private String regDate;

	// 거래 시각 (13301133)
	@Column(name = "regTImeStamp", length = 10, nullable = false)
	private String regTImeStamp;
}
