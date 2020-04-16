package com.tigerbk.kakaoByTigerBk.common;

import lombok.Data;

// 결재 상태 
public enum StateEnum {

	APPORVE("00"), 
	CANCEL("01"), 
	PARTCANCEL("02"), 
	UNKNOWN("99");

	private String result;

	StateEnum(String result) {
		this.result = result;
	}
	
	public String get() {
		return this.result;
	}

}
