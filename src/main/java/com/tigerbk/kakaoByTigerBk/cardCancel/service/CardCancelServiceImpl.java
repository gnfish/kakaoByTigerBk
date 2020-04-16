package com.tigerbk.kakaoByTigerBk.cardCancel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tigerbk.kakaoByTigerBk.cardApproved.service.CardApprovedService;
import com.tigerbk.kakaoByTigerBk.cardApproved.vo.CardPayApprovedVO;
import com.tigerbk.kakaoByTigerBk.cardCancel.repository.CardPayCancelRepository;
import com.tigerbk.kakaoByTigerBk.cardCancel.vo.CardPayCancelVO;
import com.tigerbk.kakaoByTigerBk.common.ErrorMessage;
import com.tigerbk.kakaoByTigerBk.common.StateEnum;
import com.tigerbk.kakaoByTigerBk.common.UtilsLib;
import com.tigerbk.kakaoByTigerBk.exception.RestException;
import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;

@Service
public class CardCancelServiceImpl implements CardCancelService {

	@Autowired
	CardPayCancelRepository cardpaycancelrepository;

	@Autowired
	CardApprovedService cardapprovedservice;

	// 1. 카드 취소요청 처리
	@Override
	@Transactional()
	public CardPayCancelEntity procCardCancel(CardPayCancelVO cardPayCancelVO) {

		Long cancelKey = 0L;
		Long approvedKey = 0L;
		Long cardCancelVat = 0L;
		Long cardCancelAmount = 0L;
		String regUserId = "";

		approvedKey = cardPayCancelVO.getApprovedKey();
		cardCancelVat = cardPayCancelVO.getCardVat();
		cardCancelAmount = cardPayCancelVO.getCardAmount();
		regUserId = cardPayCancelVO.getRegUserId();
		cancelKey = UtilsLib.getUniqueID();

		CardPayCancelEntity cardPayCancelEntity = new CardPayCancelEntity();

		try {

			// 전체 취소는 1번만 가능
			// 부가가치세가 안넘어오면 결제금액에서 계산한다.
			// 할부개월수 데이터는 00(일시불) 로 적재한다.

			// 1.기존 승인내역이 존재하는지 체크
			CardPayApprovedVO cardpayapprovedvo = new CardPayApprovedVO();
			cardpayapprovedvo.setApprovedKey(approvedKey);

			CardPayApprovedEntity cardPayApprovedEntity = null;
			cardPayApprovedEntity = cardapprovedservice.searchCardApproveByApprovedKey(cardpayapprovedvo);

			// 1.2.존재여부 체크
			System.out.println("procCardCancel() 1.2 존재여부체크 시작");
			if (cardPayApprovedEntity == null) {
				throw new ErrorMessage(100, "카드승인건이 없어 취소처리 할 수 없습니다!");
			}

			// 1.3. 카드승인 상태가 취소처리 가능한 상태인지 체크( 카드승인 상태가 :전체 취소 이외만 처리 가능)
			System.out.println("procCardCancel() 1.3 카드승인 상태가 취소처리 가능한 상태인지 체크 시작");
			if (cardPayApprovedEntity.getApprovedState().equals(StateEnum.CANCEL)) {
				throw new ErrorMessage(100, "이미 취소처리 된 거래로 취소처리 불가!");
			}

			// 3. 취소할수 있는 금액 체크
			// 3.1 승인금액 - 취소금액 = 취소 가능한 금액
			// 취소가능금액 < 취소 금액
			System.out.println("procCardCancel() 3. 취소할수 있는 금액 체크 시작");
			Long cardPayAmount = cardPayApprovedEntity.getCardAmount();
			Long totalCancelAmount = cardPayApprovedEntity.getCardCancelAmount();
			Long ableCancelAmount = cardPayAmount - totalCancelAmount;

			if (ableCancelAmount < cardCancelAmount) {
				System.out.println("procCardCancel() 3. 취소할수 있는 금액 체크 오류 불가처리!");
				throw new ErrorMessage(100, "취소 가능한 금액보다 취소금액이 더 큽니다!");

			}

			System.out.println("procCardCancel() 3. 취소 처리 시작");
			// 1.1 전체 취소 처리
			boolean cardApprovedUpdateRtn = false;

			if (cardCancelAmount == cardPayApprovedEntity.getCardAmount()) {
				System.out.println("procCardCancel() 3.1 전체 취소 처리 시작 : " + StateEnum.CANCEL);
				// 전체 취소처리
				// approvedState : StateEnum.CANCEL
				// cardCancelAmount : 취소 금액
				// cardCancelVat : 취소 VAT
				CardPayApprovedEntity cancelAllEntity = new CardPayApprovedEntity();
				cancelAllEntity.setApprovedKey(approvedKey);
				cancelAllEntity.setCardCancelAmount(cardCancelAmount);
				cancelAllEntity.setCardCancelVat(cardCancelVat);
				cancelAllEntity.setApprovedState(StateEnum.CANCEL.get());
				cardApprovedUpdateRtn = cardapprovedservice.updateCanCelStateByApprovedKey(cancelAllEntity);

				if (cardApprovedUpdateRtn) {
					System.out.println("procCardCancel() 전체 취소처리 updateCanCelStateByApprovedKey 업데이트 성공 !!!");
				} else {
					System.out.println("procCardCancel() 전체 취소처리 updateCanCelStateByApprovedKey 업데이트 실패 !!!");
					throw new ErrorMessage(100, "전체 취소처리 updateCanCelStateByApprovedKey 업데이트 실패 !!.");
				}

			} else {
				System.out.println("procCardCancel() 3.1 부분 취소 처리 시작 : " + StateEnum.PARTCANCEL);
				// 부분 취소 처리
				// approvedState : StateEnum.PARTCANCEL
				// cardCancelAmount : 취소 금액
				// cardCancelVat : 취소 VAT
		//		Long updateCardCancelAmt = 0L;
			

				// 부가세가 없을 경우 계산
				// 결제금액/ 11, 소수점 이하 반올림 계산
				// 1000원일 경우 91원
				// 부가가치세는 결제금액보다 클수 없습니다.
				// 결제금액이 1000원일때 부가가치세는 0원 일수 있음.
				if (cardCancelVat == 0 || cardCancelVat == null) {
					cardCancelVat = UtilsLib.CalcVat(cardCancelAmount);
				}
				
				// 취소 할수 있는 vat 값하고 취소할 vat 하고 체크
				Long cardPayVat = cardPayApprovedEntity.getCardVat();
				
				Long totalCancelVat= cardPayApprovedEntity.getCardCancelVat();
				Long ableCancelVat= cardPayVat - totalCancelVat;
				
				
				if(cardCancelVat > ableCancelVat) {
					throw new ErrorMessage(100, "취소처리할 VAT금액이 부족하여 취소 할수 없습니다. (가능한 VAT: "+ ableCancelVat+" 원)");
				}

				
				
				Long totalCardCancelAmt = cardPayApprovedEntity.getCardCancelAmount() + cardCancelAmount;
				Long  totalCardCancelVat = cardPayApprovedEntity.getCardCancelVat() + cardCancelVat;

				CardPayApprovedEntity cancelAllEntity = new CardPayApprovedEntity();
				cancelAllEntity.setApprovedKey(approvedKey);
				cancelAllEntity.setCardCancelAmount(totalCardCancelAmt);
				cancelAllEntity.setCardCancelVat(totalCardCancelVat);
				cancelAllEntity.setApprovedState(StateEnum.PARTCANCEL.get());
				cardApprovedUpdateRtn = cardapprovedservice.updateCanCelStateByApprovedKey(cancelAllEntity);
				
				if (cardApprovedUpdateRtn) {
					System.out.println("procCardCancel() 부분 취소처리 updateCanCelStateByApprovedKey 업데이트 성공 !!!");
				} else {
					System.out.println("procCardCancel() 부분 취소처리 updateCanCelStateByApprovedKey 업데이트 실패 !!!");
					throw new ErrorMessage(100, "부분 취소처리 updateCanCelStateByApprovedKey 업데이트 실패 !!.");
				}

			}

			cardPayCancelEntity.setApprovedKey(approvedKey);
			cardPayCancelEntity.setCancelKey(cancelKey);
			cardPayCancelEntity.setCancelState(StateEnum.PARTCANCEL.get());
			cardPayCancelEntity.setCancelType("00");
			cardPayCancelEntity.setCardAmount(cardCancelAmount);
			cardPayCancelEntity.setCardVat(cardCancelVat);
			cardPayCancelEntity.setRegUserId("rambo");

			// 취소 내역 적재
			cardPayCancelEntity = saveCardCancelEntity(cardPayCancelEntity);

		} catch (Exception e) {
			System.out.println("procCardCancel() Exception e : " + e.getMessage());
			e.printStackTrace();
			throw new ErrorMessage(100, e.getMessage());
		}
		return cardPayCancelEntity;
	}

	/**
	 * 카드취소내역 저장
	 * 
	 * @param cardPayCancelEntity
	 * @return cardPayCancelEntity
	 */

	private CardPayCancelEntity saveCardCancelEntity(CardPayCancelEntity cardPayCancelEntity) {
		System.out.println("procCardCancel() 최종 취소 내역 적재 시작");
		CardPayCancelEntity returnEntity = cardpaycancelrepository.save(cardPayCancelEntity);
		return returnEntity;
	}

	// 2. 카드 취소 내역 조회 서비스
	@Override
	@Transactional(readOnly = true)
	public CardPayCancelEntity searchCardCancel(CardPayCancelVO cardPayCancelVO) {

		CardPayCancelEntity returnVo = null;
		try {
			returnVo = cardpaycancelrepository.findById(cardPayCancelVO.getCancelKey())
					.orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "카드 취소 내역 조회 데이타 존재하지 않습니다."));
		} catch (Exception e) {
			new RestException(HttpStatus.NOT_ACCEPTABLE, "카드취소 처리가 실패되었습니다.");
		}

		return returnVo;
	}
}
