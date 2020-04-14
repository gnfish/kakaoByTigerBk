package com.tigerbk.kakaoByTigerBk.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerbk.kakaoByTigerBk.config.ResultEnum;
import com.tigerbk.kakaoByTigerBk.entity.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.entity.CardPayCancelEntity;
import com.tigerbk.kakaoByTigerBk.services.CardService;
import com.tigerbk.kakaoByTigerBk.vo.ResultVO;

@RestController
public class CardController {

	@Autowired
	CardService cardService;

	@PostMapping("/cardApprove")
	public String procCardApporve() {

		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();

		// 최종 결과 전송
		resultVO.setResultCode(ResultEnum.SUCCESS);
		resultVO.setResultMsg("카드승인 처리되었습니다.");
		result.put("resultVO", resultVO);
		return result.toString();
	} 

	@PostMapping("/cardCancel")
	public String procCardCancel() {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();

		// 최종 결과 전송
		resultVO.setResultCode(ResultEnum.SUCCESS);
		resultVO.setResultMsg("카드취소가 정상 처리되었습니다.");
		result.put("resultVO", resultVO);
		return result.toString();
	}

	@GetMapping("/searchCardApprorve")
	public String searchCardApprorveList() {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();

		// 1 기본 결제 uid로 조회
		// 2 추가로 사용자ID , 결제일 로 조회 기능 추가
		CardPayApprovedEntity returnVal = new CardPayApprovedEntity();
		Long uid = (long) 11;

		returnVal = cardService.searchCardApprove(uid);

		if (returnVal == null) {

		}
		result.put("returnVal", returnVal);

		// 최종 결과 전송
		resultVO.setResultCode(ResultEnum.SUCCESS);
		resultVO.setResultMsg("카드취소가 정상 처리되었습니다.");
		result.put("resultVO", resultVO);
		return result.toString();
	}

	@GetMapping("/searchCardCancel")
	public String searchCardCancel() {
		// 1. 초기 변수 셋팅
		HashMap<String, Object> result = new HashMap<>();
		ResultVO resultVO = new ResultVO();
		// 1 기본 결제 uid로 조회
		// 2 추가로 사용자ID , 결제일 로 조회 기능 추가
		CardPayCancelEntity returnVal = new CardPayCancelEntity();
		Long uid = (long) 11;
		returnVal = cardService.searchCardCancel(uid);
		result.put("returnVal", returnVal);
		// 최종 결과 전송
		resultVO.setResultCode(ResultEnum.SUCCESS);
		resultVO.setResultMsg("카드취소가 정상 처리되었습니다.");
		result.put("resultVO", resultVO);
		return result.toString();
	}

}
