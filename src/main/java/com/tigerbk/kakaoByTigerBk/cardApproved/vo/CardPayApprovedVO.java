package com.tigerbk.kakaoByTigerBk.cardApproved.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardPayApprovedVO {

	private Long approvedKey;
	@NotEmpty
	@NotBlank
	@Size(min = 3, max = 50)
	private String regUserId;
	// 카드번호(10~16번자)
	@NotEmpty
	@NotBlank
	@Size(min = 12, max = 13)
	private String cardNumber;
	// 유효기간(4자리)
	@NotEmpty
	@NotBlank
	@Size(min = 4, max = 4)
	private String cardExpiredDate;
	// cvc(3자리숫자)
	@NotEmpty
	@NotBlank
	@Size(min = 3, max = 3)
	private String cardCvc;
	// 할부개월수 1~12
	@NotNull
	@Size(min = 2, max = 2)
	private String cardPeriod;
	// 10억원이하 숫자
	@NotNull
	private Long cardAmount;
	// 10억원이하 숫자cardVat
	@NotNull
	private Long cardVat;

}
