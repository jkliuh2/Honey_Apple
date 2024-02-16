package com.honeyapple.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat")
@RestController
public class ChatRestController {

	
	// /chat/trade-reservation-toggle
	// 예약or예약취소 토글 API - 예약중복확인도 처리해야 함            ////아직 덜만듬////
	@PostMapping("/trade-reservation-toggle")
	public Map<String, Object> tradeReservationToggle(
			@RequestParam("chatId") int chatId,
			HttpSession session) {
		
		// BO로 데이터 보내기 - 리턴(1, 2, 3)값으로 응답값 정하기
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("success_message", "테스트용 성공 메시지");
		return result;
	}
}
