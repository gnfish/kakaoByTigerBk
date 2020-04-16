package com.tigerbk.kakaoByTigerBk.cardApproved.controller;

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

import com.tigerbk.kakaoByTigerBk.cardApproved.service.CardApprovedService;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorCodeEnum;
import com.tigerbk.kakaoByTigerBk.common.vo.ResultVO;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;

@RestController
public class CardApprovedController {

	// 서비스 의존성 주입
	@Autowired
	CardApprovedService cardapprovedservice;

	@PostMapping("/cardApprove")	
	public HashMap<String, Object> CardApprovedController(@RequestBody @Valid CardPayApprovedVO cardpayapprovedvo) {

		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();
		ErrorCodeEnum resultCode = ErrorCodeEnum.PROC_SUCCESS;
		System.out.println("입력받은 데이타 : " + cardpayapprovedvo.toString());
		// 2. 카드 승인 요청
		try {
			// 2.1 카드 승인 저장
			CardPayApprovedEntity cardPayApprovedEntity = cardapprovedservice.procCardApprove(cardpayapprovedvo);

			// 2.2 전문 통신 저장
			if (!"".equals(cardPayApprovedEntity.getApprovedKey())) {
				result.put("approvedNo", cardPayApprovedEntity.getApprovedKey());
			} else {
				result.put("approvedNo", cardPayApprovedEntity.getApprovedKey());
				resultCode = ErrorCodeEnum.SYSTEM_ERROR;
			}

		} catch (Exception e) {
			result.put("approvedNo", "");
			System.out.println("Error =>>>>>>>> " + e.getMessage());
			resultCode = ErrorCodeEnum.SYSTEM_ERROR;
		}
		// 3. 최종 결과 전송
		resultVO.setResultCode(resultCode.getCode());
		resultVO.setResultMsg(resultCode.getMessage());
		result.put("resultVO", resultVO);
		return result;
	}

	@GetMapping("/searchCardApprorve/{approvedKey}")
	public HashMap<String, Object>  searchCardApprorveList(@PathVariable("approvedKey") Long approvedKey) {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();
		ErrorCodeEnum resultCode = ErrorCodeEnum.SEARCH_SUCCESS;

		// 2. 카드 승인 저장
		try {
			CardPayApprovedVO cardpayapprovedvo = new CardPayApprovedVO();
			cardpayapprovedvo.setApprovedKey(approvedKey);

			// 2.1 기본 결제 uid로 조회
			// 2.2 추가로 사용자ID , 결제일 로 조회 기능 추가
			CardPayApprovedEntity returnVal = new CardPayApprovedEntity();
			returnVal = cardapprovedservice.searchCardApproveByApprovedKey(cardpayapprovedvo);

			if (returnVal == null) {
				result.put("returnVal", "");
				resultCode = ErrorCodeEnum.SEARCH_SUCCESS;
			} else {
				result.put("returnVal", returnVal);
			}

		} catch (Exception e) {
			result.put("returnVal", "");
			resultCode = ErrorCodeEnum.SYSTEM_ERROR;
		}
		// 3. 최종 결과 전송
		resultVO.setResultCode(resultCode.getCode());
		resultVO.setResultMsg(resultCode.getMessage());
		result.put("resultVO", resultVO);
		
		return result;
	}

}
