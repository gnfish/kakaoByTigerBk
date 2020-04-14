package com.tigerbk.kakaoByTigerBk.config;

import lombok.Getter;

@Getter
public enum ResultEnum {

	SUCCESS(0), DB_ERR(1), TIMEOUT(2), UNKNOWN(99);

	private int result;

	private ResultEnum(int result) {
		this.result = result;
	}

}
