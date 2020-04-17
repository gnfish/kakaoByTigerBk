package com.tigerbk.kakaoByTigerBk.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CardPaySendDataEntity")
public class CardPaySendDataEntity {

	// 20자리
	@Id
	@Column(name = "sendDataKey")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long sendDataKey;
	// 거래 시각
	@CreationTimestamp
	@Column(name = "createdTimeAt", length = 8, nullable = false, updatable = false)
	private LocalDateTime createdTimeAt;

	// PAYMENT : 카드 승인 요청
	// CALCEL : 카드 취소 요청
	@Column(name = "bizNo", nullable = false, updatable = false)
	private String bizNo;
	
	// 0200 : 카드 승인 요청
	// 0210 : 카드 취소 요청
	@Column(name = "dealKey", nullable = false, updatable = false)
	private Long dealKey;
	

	// 총 450 byte
	@Column(name = "sendData", length = 450, nullable = false, updatable = false)
	private String sendData;

	// 전송 성공 여부
	@Column(name = "SendResult", length = 2, nullable = false, updatable = false)
	private String SendResult;

}
