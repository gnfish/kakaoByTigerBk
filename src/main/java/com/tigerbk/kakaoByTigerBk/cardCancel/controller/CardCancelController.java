package com.tigerbk.kakaoByTigerBk.cardCancel.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tigerbk.kakaoByTigerBk.cardCancel.service.CardCancelService;
import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.common.vo.ResultVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;

@RestController
public class CardCancelController {

	@Autowired
	CardCancelService cardcancelservice;

	@PostMapping("/cardCancel")
	public HashMap<String, Object> procCardCancel(@RequestBody @Valid CardPayCancelVO cardPayCancelVO) {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();
		ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;
		System.out.println("입력받은 데이타 : " + cardPayCancelVO.toString());
		String errMessage = "성공!!";

		// 2.취소 처리 프로세스
		try {
			CardPayCancelEntity cardpaycancelentity = cardcancelservice.procCardCancel(cardPayCancelVO);
			result.put("cancelKey", cardpaycancelentity.getCancelKey());
		} catch (Exception e) {
			result.put("cancelKey", "");
			System.out.println("Error =>>>>>>>> " + e.getMessage());
			errMessage = e.getMessage();
			resultCode = ErrorCodeEnum.SYSTEM_ERROR;

		}

		// 3. 최종 결과 전송
		resultVO.setResultCode(resultCode.getCode());
		resultVO.setResultMsg(errMessage);
		result.put("resultVO", resultVO);
		return result;
	}

	@GetMapping("/searchCardCancel/{cancelKey}")
	public HashMap<String, Object> searchCardCancel(@PathVariable("cancelKey") Long cancelKey) {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();
		ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;

		// 2. 카드 취소 조회 시작
		try {
			CardPayCancelVO cardpaycancelvo = new CardPayCancelVO();
			cardpaycancelvo.setCancelKey(cancelKey);

			// 2.1 기본 결제 uid로 조회
			// 2.2 추가로 사용자ID , 결제일 로 조회 기능 추가
			CardPayCancelEntity returnVal = new CardPayCancelEntity();

			returnVal = cardcancelservice.searchCardCancel(cardpaycancelvo);
			if (returnVal == null) {
				result.put("returnVal", "");
				resultCode = ErrorCodeEnum.NOT_FOUND;
			} else {
				result.put("returnVal", returnVal);
			}

		} catch (Exception e) {
			result.put("returnVal", "");
			resultCode = ErrorCodeEnum.SYSTEM_ERROR;
		}

		// 3.최종 결과 전송
		resultVO.setResultCode(resultCode.getCode());
		resultVO.setResultMsg(resultCode.getMessage());
		result.put("resultVO", resultVO);
		return result;
	}

}
