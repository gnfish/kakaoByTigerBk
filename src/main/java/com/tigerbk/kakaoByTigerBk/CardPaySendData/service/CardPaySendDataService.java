package com.tigerbk.kakaoByTigerBk.CardPaySendData.service;

import com.tigerbk.kakaoByTigerBk.models.CardPayApprovedEntity;
import com.tigerbk.kakaoByTigerBk.models.CardPayCancelEntity;
import com.tigerbk.kakaoByTigerBk.models.CardPaySendDataEntity;

public interface CardPaySendDataService {
	
	CardPaySendDataEntity sendData(CardPayApprovedEntity cardPayApprovedEntity);
	
	CardPaySendDataEntity sendData(CardPayCancelEntity cardPayCancelEntity);
	
	CardPaySendDataEntity saveData(CardPaySendDataEntity cardPaySendDataEntity) ;
	
}
