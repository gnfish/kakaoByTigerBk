package com.tigerbk.kakaoByTigerBk.common;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
	
	PROC_SUCCESS("00","정상적으로 처리되었습니다."), 
	SEARCH_SUCCESS("00","정상적으로 조회 되었습니다."), 
	
	SYSTEM_ERROR("99","시스템 오류가 발생했습니다."), 
	
	NOT_FOUND("404", "데이타가 존재하지 않습니다."), 
	UNREGISTERED_KEY("020", "등록되지 않은 키입니다."), 
	RESERVED("200", "접근이 거부되었습니다.");

	private String code;
	private String message;

	private ErrorCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
