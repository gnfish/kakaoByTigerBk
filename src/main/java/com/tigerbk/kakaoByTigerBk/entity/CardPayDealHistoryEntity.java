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
@Table(name = "CardPayDealHistoryEntity")
public class CardPayDealHistoryEntity {

		// 20자리
		@Id
		@NonNull
		@Column(name="uID")	
		private Long  uID; 
	// 거래일자 (20200405)
	   @Column(name="regDate" , length=8, nullable=false )
		private String regDate;	
	// 거래 시각 (13301133)
	   @Column(name="regTImeStamp" , length=10, nullable=false )
		private String regTImeStamp;	
		
	   	// 4(데이타총길이), 10(PAYMENT/CANCEL) , 20(Uid)
		@Column(name="header" , length=34, nullable=false )
		private String header;
		
		// 카드번호(10~16번자)
		@Column(name="Body" , length=416, nullable=false )
		private String Body;}
