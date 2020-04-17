package com.tigerbk.kakaoByTigerBk.CardPaySendData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigerbk.kakaoByTigerBk.CardPaySendData.repostitory.CardPaySendDataRepository;
import com.tigerbk.kakaoByTigerBk.common.AES256Util;
import com.tigerbk.kakaoByTigerBk.common.UtilsLib;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;
import com.tigerbk.kakaoByTigerBk.models.CardPaySendDataEntity;

@Service
public class CardPaySendDataServiceImpl implements CardPaySendDataService {

	@Autowired
	CardPaySendDataRepository cardPaySendDataRepository;

	@Override
	public CardPaySendDataEntity sendData(CardPayApprovedEntity cardPayApprovedEntity) {

		// 1. header 를 조립한다.
		StringBuffer headerData = new StringBuffer(34);

		// 34 byte
		String datalen = "450"; // 데이터 길이 숫자 4 "데이터 길이"를 제외한 총 길이
		String dataGuben = "PAYMENT"; // 데이터 구분 문자 10 기능 구분값, 승인(PAYMENT), 취소(CANCEL)
		String sendDataKey = ""; // 관리번호 문자 20 unique id(20자리)
		// 2. body 를 조립니다.
		headerData.append(UtilsLib.strAdd(datalen, "_", "R", 4));
		headerData.append(UtilsLib.strAdd(dataGuben, "_", "L", 10)); // 좌측 정렬, 빈공간 채우기
		headerData.append(UtilsLib.strAdd(sendDataKey, "_", "R", 20));

		System.out.println(" headerData : " + headerData.toString());
		StringBuffer bodyData = new StringBuffer(416);
//		bodyData.append(UtilsLib.strAdd("20R", "_", "R", 20)); // 카드번호 숫자(L)
//		bodyData.append(UtilsLib.strAdd("2L", "0", "L", 2)); // 할부개월수 숫자 L
//		bodyData.append(UtilsLib.strAdd("4L", "0", "L", 4)); // 유효기간숫자 L
//		bodyData.append(UtilsLib.strAdd("3L", "0", "L", 3)); // cvc숫자 L
//		bodyData.append(UtilsLib.strAdd("10R", "_", "R", 10)); // 거래금액
//		bodyData.append(UtilsLib.strAdd("10L", "0", "L", 10)); // 부가세
//		bodyData.append(UtilsLib.strAdd("20L", "_", "L", 20)); // 취소시에만 결제 관리번호 저장 결제시에는 공백
//		bodyData.append(UtilsLib.strAdd("300L", "_", "L", 300)); // 암/복호화 방식 자유롭게 선택
//		bodyData.append(UtilsLib.strAdd("47L", "_", "L", 47)); // 향후 생길 데이터를 위해 남겨두는 공간
		bodyData.append(UtilsLib.strAdd(cardPayApprovedEntity.getCardNumber(), "_", "R", 20)); // 카드번호 숫자(L)
		bodyData.append(UtilsLib.strAdd(cardPayApprovedEntity.getCardPeriod(), "0", "L", 2)); // 할부개월수 숫자 L
		bodyData.append(UtilsLib.strAdd(cardPayApprovedEntity.getCardExpiredDate(), "0", "L", 4)); // 유효기간숫자 L
		bodyData.append(UtilsLib.strAdd(cardPayApprovedEntity.getCardCvc(), "0", "L", 3)); // cvc숫자 L
		bodyData.append(UtilsLib.strAdd(String.valueOf(cardPayApprovedEntity.getCardAmount()), "_", "R", 10)); // 거래금액
		bodyData.append(UtilsLib.strAdd(String.valueOf(cardPayApprovedEntity.getCardVat()), "0", "L", 10)); // 부가세
		bodyData.append(UtilsLib.strAdd(" ", "_", "L", 20)); // 취소시에만 결제 관리번호 저장 결제시에는 공백
		String encryptData = "";
		try {
			AES256Util ase = new AES256Util("1234567890123456");

			encryptData = cardPayApprovedEntity.getCardNumber() + "|" + cardPayApprovedEntity.getCardPeriod() + "|"
					+ cardPayApprovedEntity.getCardExpiredDate();
			encryptData = ase.aesEncode(encryptData);
			System.out.println("encryptData:" + encryptData);
		} catch (Exception e) {
			e.printStackTrace();

		}

		bodyData.append(UtilsLib.strAdd("!_" + encryptData + "_!", "_", "L", 300)); // 암/복호화 방식 자유롭게 선택
		bodyData.append(UtilsLib.strAdd("47L", "_", "L", 47)); // 향후 생길 데이터를 위해 남겨두는 공간

		System.out.println("bodyData:" + bodyData.toString());
		StringBuffer sendData = new StringBuffer(450);
		sendData.append(headerData);
		sendData.append(bodyData);
		System.out.println("sendData:" + sendData.toString());
		CardPaySendDataEntity cardPaySendDataEntity = new CardPaySendDataEntity();
		// cardPaySendDataEntity.setSendDataKey(cardPaySendDataEntity.getSendDataKey());
		cardPaySendDataEntity.setBizNo("PAYMENT");
		cardPaySendDataEntity.setDealKey(cardPayApprovedEntity.getApprovedKey());
		cardPaySendDataEntity.setSendData(sendData.toString());
		cardPaySendDataEntity = this.saveData(cardPaySendDataEntity);
		return cardPaySendDataEntity;
	}

	@Override
	public CardPaySendDataEntity sendData(CardPayCancelEntity cardPayCancelEntity) {

		// 1. header 를 조립한다.
		StringBuffer headerData = new StringBuffer(34);
		// 34 byte
		String datalen = "450"; // 데이터 길이 숫자 4 "데이터 길이"를 제외한 총 길이
		String dataGuben = "CANCEL"; // 데이터 구분 문자 10 기능 구분값, 승인(PAYMENT), 취소(CANCEL)
		String sendDataKey = ""; // 관리번호 문자 20 unique id(20자리)
		// 2. body 를 조립니다.
		headerData.append(UtilsLib.strAdd(datalen, "_", "R", 4));
		headerData.append(UtilsLib.strAdd(dataGuben, "_", "L", 10)); // 좌측 정렬, 빈공간 채우기
		headerData.append(UtilsLib.strAdd(sendDataKey, "_", "R", 20));
		System.out.println(" headerData : " + headerData);
		StringBuffer bodyData = new StringBuffer(416);
//		bodyData.append(UtilsLib.strAdd("20R", "_", "R", 20)); // 카드번호 숫자(L)
//		bodyData.append(UtilsLib.strAdd("2L", "0", "L", 2)); // 할부개월수 숫자 L
//		bodyData.append(UtilsLib.strAdd("4L", "0", "L", 4)); // 유효기간숫자 L
//		bodyData.append(UtilsLib.strAdd("3L", "0", "L", 3)); // cvc숫자 L
//		bodyData.append(UtilsLib.strAdd("10R", "_", "R", 10)); // 거래금액
//		bodyData.append(UtilsLib.strAdd("10L", "0", "L", 10)); // 부가세
//		bodyData.append(UtilsLib.strAdd("20L", "_", "L", 20)); // 취소시에만 결제 관리번호 저장 결제시에는 공백
//		bodyData.append(UtilsLib.strAdd("300L", "_", "L", 300)); // 암/복호화 방식 자유롭게 선택
//		bodyData.append(UtilsLib.strAdd("47L", "_", "L", 47)); // 향후 생길 데이터를 위해 남겨두는 공간

		bodyData.append(UtilsLib.strAdd("20R", "_", "R", 20)); // 카드번호 숫자(L)
		bodyData.append(UtilsLib.strAdd("2L", "0", "L", 2)); // 할부개월수 숫자 L
		bodyData.append(UtilsLib.strAdd("4L", "0", "L", 4)); // 유효기간숫자 L
		bodyData.append(UtilsLib.strAdd("3L", "0", "L", 3)); // cvc숫자 L
		bodyData.append(UtilsLib.strAdd(String.valueOf(cardPayCancelEntity.getCardAmount()), "_", "R", 10)); // 거래금액
		bodyData.append(UtilsLib.strAdd(String.valueOf(cardPayCancelEntity.getCardVat()), "0", "L", 10)); // 부가세
		bodyData.append(UtilsLib.strAdd("20L", "_", "L", 20)); // 취소시에만 결제 관리번호 저장 결제시에는 공백
		bodyData.append(UtilsLib.strAdd("300L", "_", "L", 300)); // 암/복호화 방식 자유롭게 선택
		bodyData.append(UtilsLib.strAdd("47L", "_", "L", 47)); // 향후 생길 데이터를 위해 남겨두는 공간

		System.out.println("bodyData:" + bodyData);
		StringBuffer sendData = new StringBuffer(450);
		sendData.append(headerData);
		sendData.append(bodyData);
		System.out.println("sendData:" + sendData);
		CardPaySendDataEntity cardPaySendDataEntity = new CardPaySendDataEntity();
		cardPaySendDataEntity.setBizNo("CANCEL");
		cardPaySendDataEntity.setDealKey(cardPayCancelEntity.getCancelKey());
		cardPaySendDataEntity.setSendData(sendData.toString());
		cardPaySendDataEntity = this.saveData(cardPaySendDataEntity);
		return cardPaySendDataEntity;
	}

	/*
	 * 헤더 메이커
	 */

	/*
	 * 실제 통신
	 */
	public CardPaySendDataEntity saveData(CardPaySendDataEntity cardPaySendDataEntity) {
		CardPaySendDataEntity cardPaySendDataEntity1 = new CardPaySendDataEntity();
		cardPaySendDataEntity.setSendResult("00");
		cardPaySendDataEntity1 = cardPaySendDataRepository.save(cardPaySendDataEntity);
		return cardPaySendDataEntity1;
	}

}
