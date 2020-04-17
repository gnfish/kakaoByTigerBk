package com.tigerbk.kakaoByTigerBk.cardCancel.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CardPayCancelVO {
	//카드 승인 키
	@NotNull
	private Long approvedKey;	
	//카드 취소 키
	private Long cancelKey;		
	// 사용자ID
	private String regUserId;
	//카드 취소 금액
	@NotNull
	private Long cardAmount;
	//카드 취소 vat
	private Long cardVat;
}
