package com.tigerbk.kakaoByTigerBk.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@Setter
@EqualsAndHashCode(callSuper = false)
public class ErrorMessage extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	private String errorMessage;

	public ErrorMessage(int code, String errorMessagel) {
		super(errorMessagel);
		this.code = code;
		this.errorMessage = errorMessage;
	}
}
