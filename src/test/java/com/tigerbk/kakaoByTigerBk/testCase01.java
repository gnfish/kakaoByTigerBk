package com.tigerbk.kakaoByTigerBk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class testCase01 {

	@Test
	@DisplayName("@@  1번 테스트 케이스 @@ ")
	public void testCase() {
		
		
		
		
		
		System.out.println(" 1번 테스트 케이스 성공 !");		
		assertEquals(2, 2);
	}
		
}
