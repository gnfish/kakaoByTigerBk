package com.tigerbk.kakaoByTigerBk.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CardPayDealInfoEntity")
public class CardPayDealInfoEntity {
	@Id
	@Column(name = "regUserId", length = 100, nullable = false)
	private String regUserId;

	// 카드번호(10~16번자)
	@Column(name = "cardNumber", nullable = false)
	@Size(min = 10, max = 16)
	private String cardNumber;

}
