package com.tigerbk.kakaoByTigerBk.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UtilsLib {

	/**
	 * 유니크 Key 구하기 20자리
	 * 
	 * @param bytes
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static Long getUniqueID() {

		UUID idOne = UUID.randomUUID();
		String str = "" + idOne;
		int uid = str.hashCode();
		String filterStr = "" + uid;
		str = filterStr.replaceAll("-", "");
		return Long.parseLong(str);

	}

	/**
	 * VAT 계산 결제금액/ 11, 소수점 이하 반올림 계산 1000원일 경우 91원 부가가치세는 결제금액보다 클수 없습니다. 결제금액이
	 * 1000원일때 부가가치세는 0원 일수 있음.
	 * @param bytes
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static Long CalcVat(Long amount) {
		Long retVat = 0L;
		if (amount == 0) {
			return retVat;
		}
		retVat = (long) Math.round((amount / 11));
		return retVat;
	}
	
	
	/**
	 * SHA-256으로 해싱하는 메소드
	 * 
	 * @param bytes
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] sha256(String msg) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(msg.getBytes());
	    
	    return md.digest();
	}

	
	
	
	
	

	/**
	 * 대외 전문 전송이나 연계를 위한 변환 API. VO 객체를 byte[]로 생성.
	 * 
	 * @param obj byte[] 로 변환될 VO 객체.
	 * @param charSet byte 변환시 사용된 Encoding Character Set.
	 * 
	 * @return VO 객체에서 변환 된 byte[]
	 */
	/*
	 * public static byte[] toBytes(Object obj, String charSet) { return
	 * toBytes(obj, charSet, null); }
	 */

	/**
	 * byte[] 전문 생성시 ConversionConfiguration 정보를 통해 가변길이 처리를 추가적으로 수행하여 처리 합니다.
	 * ConversionConfiguration이 제공하는 추가 속성은 VO에서 List 나 [] 에 사용하는 ArraySize 어노테이션에서 variable=true
	 * 속성이 정의된 필드에 대해서 maxDigit과 Padding 속성을 지정할 수 있습니다.
	 * 
	 * @param obj byte[] 로 변환될 VO 객체.
	 * @param charSet byte 변환시 사용될 Encoding Character Set.
	 * @param conversionConfiguration 가변길이 List 또는 []처리시 가변 길이 정보 기록을 위한 최대 자리수(maxDigit)와 Padding(0, ' ')을 지정합니다.
	 * @return VO 객체에서 변환 된 byte[]
	 */
	/*
	 * public static byte[] toBytes(Object obj, String charSet,
	 * ConversionConfiguration conversionConfiguration) { ByteEncoder byteEncoder =
	 * new ByteEncoder(obj, charSet, conversionConfiguration); return
	 * byteEncoder.encode(); }
	 */
	
	
}
