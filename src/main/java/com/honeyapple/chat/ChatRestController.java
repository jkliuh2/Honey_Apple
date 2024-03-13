package com.honeyapple.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honeyapple.chat.bo.ChatServiceBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat")
@RestController
public class ChatRestController {
	
	@Autowired
	private ChatServiceBO chatServiceBO;

	
	// /chat/trade-reservation-toggle
	// 예약or예약취소 토글 API
	@PostMapping("/trade-reservation-toggle")
	public Map<String, Object> tradeReservationToggle(
			@RequestParam("chatId") int chatId,
			HttpSession session) {
		
		// 본인확인 -> js에서 처리함.
		
		// BO로 데이터 보내기 - 리턴값으로 응답값 정하기
		String resultStr = chatServiceBO.reservationToggle(chatId);
		Map<String, Object> result = new HashMap<>();
		
		if (resultStr.equals("예약완료") || resultStr.equals("예약취소완료")) {
			result.put("code", 200);
			result.put("success_message", resultStr);
		} else if (resultStr.equals("다른사람예약")) {
			result.put("code", 300);
			result.put("error_message", "이미 다른 예약이 존재합니다.");
		} else if (resultStr.equals("판매완료")) {
			result.put("code", 301);
			result.put("error_message", "판매가 완료된 물품입니다.");
		} else {
			result.put("code", 500);
			result.put("error_message", "거래 예약에 실패했습니다.");
		}
		return result;
	}
}
