package com.tigerbk.kakaoByTigerBk.CardPaySendData.vo;


import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CardPaySendDataVO {

	//@Length(value = 20)
	private String CardNumber;
	
}
