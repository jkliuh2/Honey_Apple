package com.honeyapple.review;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honeyapple.review.bo.ReviewServiceBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

	@Autowired
	private ReviewServiceBO reviewServiceBO;
	
	
	// 거래완료 API
	@PostMapping("/complete-trade")
	public Map<String, Object> completeTrade(
			@RequestParam("chatId") int chatId,
			@RequestParam("score") int score,
			@RequestParam(name = "review", required = false) String review,
			HttpSession session) {
		
		// 세션에서 로그인 정보 꺼내기
		int userId = (int)session.getAttribute("userId");
		
		// BO로(1-정상처리 / 2-유저가 다른 사람)
		int code = reviewServiceBO.completeTrade(userId, chatId, score, review);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (code == 1) {
			result.put("code", 200);
		} else if (code == 2) {
			result.put("code", 501);
			result.put("error_message", "현재 거래의 구매자와 로그인 정보가 일치하지 않습니다.");
		}
		return result;
	}
}
