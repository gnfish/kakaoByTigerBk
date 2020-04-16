package com.tigerbk.kakaoByTigerBk.cardCancel.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CardPayCancelVO {
	@NotNull
	private Long approvedKey;
	
	private Long cancelKey;	
	
	@Size(min = 3, max = 50)
	private String regUserId;
	@NotNull
	private Long cardAmount;
	// 10억원이하 숫자cardVat
	@NotNull
	private Long cardVat;
}
