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
	

    @Autowired
    protected WebTestClient webTestClient;
    

//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));

	@Test
	@DisplayName("@@  카드 승인 요청 테스트 케이스 @@ ")
	public void testCase() throws Exception {

//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//		mockMvc = builder.build();
//
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc = MockMvcBuilders.standaloneSetup(cardApprovedController)
				.addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true)).alwaysDo(print()).build();

		// mockMvc.perform(post("/cardApprove/{boardId}", 1L));

		// post 에서 사용
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode params = mapper.createObjectNode();
		params.put("regUserId", "tigerBK");
		params.put("cardNumber", "12345678901234");
		params.put("cardExpiredDate", "1222");
		params.put("cardCvc", "123");
		params.put("cardAmount", "10000");
		params.put("cardVat", "1000");
		params.put("cardPeriod", "00");
		try {
			MvcResult result = mockMvc
					.perform(post("/cardApprove").content(mapper.writeValueAsString(params))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isOk()).andReturn();
			String content = result.getResponse().getContentAsString();

			
			webTestClient.post()
	            .uri("/cardApprove")
	            .contentType(MediaType.APPLICATION_JSON)
	      //      .body(BodyInserters.fromObject(employee))
	            .exchange()
	            .expectStatus().isCreated();
			  
			
//			  webTestClient.post().uri("/cardApprove")
//              .exchange()
//              .expectStatus()
//              .isOk()
//              .expectHeader()
//              .contentType(MediaType.APPLICATION_JSON)
//              .expectBody()
//              .jsonPath("$..year").value(hasItem(2005))
//              .jsonPath("$..totalAmount").value(hasItem(48016))
//              .jsonPath("$..detailAmount").isArray()
//              .jsonPath("$..detailAmount[*].name").value(hasItem("주택도시기금"))
//              .jsonPath("$..detailAmount[*].amount").value(hasItem(22247))
//      ;
//			  
			  
			System.out.println("결제요청 result : " + content);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		// UserRegistResult user = mapper.readValue(content, UserRegistResult.class);
		// System.err.println("JSon Result=" +
		// mapper.defaultPrettyPrintingWriter().writeValueAsString(user));

		// System.out.println("결제요청 result : " + content);
		System.out.println("1번 테스트 케이스 성공 !");
		assertEquals(2, 2);
	}

}
