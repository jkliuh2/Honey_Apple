package com.honeyapple.interest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honeyapple.interest.bo.InterestBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class InterestRestController {

	@Autowired
	private InterestBO interestBO;
	
	
	// 관심 토글 API
	@RequestMapping("/interest/{postId}")
	public Map<String, Object> interestToggle(
			@PathVariable(name = "postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 세션에서 로그인정보 꺼내기
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 로그인 만료
			result.put("code", 300);
			result.put("error_message", "로그인이 만료되었습니다.");
			return result;
		}
		
		// DB
		interestBO.interestToggle(postId, userId);
		result.put("code", 200);
		
		return result;
	}
}
