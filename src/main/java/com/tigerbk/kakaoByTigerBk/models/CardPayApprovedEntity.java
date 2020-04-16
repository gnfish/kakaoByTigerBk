package com.tigerbk.kakaoByTigerBk.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "CardPayApprovedEntity")
// 카드승인내역 테이블
public class CardPayApprovedEntity {

	@Id
	@NonNull
	@Column(name = "approvedKey",  length = 20, updatable = false)
	private Long approvedKey; // 20자리

	@Column(name = "regUserId", length = 100, nullable = false, updatable = false)
	private String regUserId;

	// 카드 승인 상태(2자리)
	@Column(name = "approvedState", length = 10, nullable = false)
	private StateEnum approvedState;

	// 카드번호(10~16번자)
	@Column(name = "cardNumber", nullable = false, updatable = false)
	@Size(min=10, max=16)
	private String cardNumber;

	// 유효기간(4자리)
	@Column(name = "cardExpiredDate", length = 4, nullable = false, updatable = false)
	private String cardExpiredDate;

	// cvc(3자리숫자)
	@Column(name = "cardCvc", length = 3, nullable = false, updatable = false)
	private String cardCvc;

	// 할부개월수 1~12 , 일시불 : 00 
	@ColumnDefault("00") //default 0
	@Column(name = "cardPeriod", length = 2, nullable = false, updatable = false)
	private String cardPeriod;

	// 10억원이하 숫자
	@Column(name = "cardAmount", nullable = false, updatable = false)
	private Long cardAmount;

	// 부가가치세
	@Column(name = "cardVat", nullable = false, updatable = false)
	private Long cardVat;
	
	// 취소 금액
	@ColumnDefault("0") //default 0
	@Column(name = "cardCancelAmount")
	private Long cardCancelAmount;

	// 취소부가가치세
	@ColumnDefault("0") //default 0
	@Column(name = "cardCancelVat")
	private Long cardCancelVat;	

	// 거래 시각 
	@CreationTimestamp
	@Column(name = "createdTimeAt", nullable = false , updatable = false)
	private LocalDateTime createdTimeAt;

	// 수정 시각 
	@UpdateTimestamp
	@Column(name = "updateTimeAt",  nullable = false , updatable = false)
	private LocalDateTime updateTimeAt;

}
