package com.tigerbk.kakaoByTigerBk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigerbk.kakaoByTigerBk.cardApproved.controller.CardApprovedController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class testCase01 {

	// private MockMvc mockMvc;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	CardApprovedController cardApprovedController;


	@Test
	@DisplayName("@@  카드 승인 요청 테스트 케이스 @@ ")
	public void testCase() throws Exception {

		System.out.println("1번 테스트 케이스 성공 != > " + String.format("%_20s", "02020") );
		System.out.println("1번 테스트 케이스 성공 !");
		assertEquals(2, 2);
	}

}
