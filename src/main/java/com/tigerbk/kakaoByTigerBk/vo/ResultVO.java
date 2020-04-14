package com.tigerbk.kakaoByTigerBk.vo;

import com.tigerbk.kakaoByTigerBk.config.ResultEnum;

import lombok.Data;

@Data
public class ResultVO {

	public ResultEnum resultCode;
	public Object resultMsg;
}
