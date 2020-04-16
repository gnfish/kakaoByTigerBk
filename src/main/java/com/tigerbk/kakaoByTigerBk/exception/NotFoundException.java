package com.tigerbk.kakaoByTigerBk.exception;

public class NotFoundException extends RuntimeException {
	private static final String MESSAGE = "찾을 수 없는 기관입니다.";

	public NotFoundException() {
		super(MESSAGE);
	}
}
