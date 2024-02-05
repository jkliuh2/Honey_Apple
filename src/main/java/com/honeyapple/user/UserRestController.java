package com.honeyapple.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honeyapple.common.EncryptUtils;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;

	// 회원가입 API
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("nickname") String nickname,
			@RequestParam("password") String password,
			@RequestParam("email") String email
			) {
		
		// 비밀번호 hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB insert
		UserEntity user = userBO.addUser(loginId, nickname, hashedPassword, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
		} else {
			result.put("code", 500);
			result.put("error_message", "회원정보를 DB에 저장하는데 실패했습니다.");
		}
		return result;
	}
	
	
	// loginId 중복확인 API
	@GetMapping("/is-duplicated-loginId")
	public Map<String, Object> isDuplicatedLoginId(
			@RequestParam("loginId") String loginId) {
		
		// DB select
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 중복O
			result.put("code", 200);
			result.put("is_duplicated_loginId", true);
		} else {
			result.put("code", 200);
			result.put("is_duplicated_loginId", false);
		}
		
		return result;
	}
	
	
	// 닉네임 중복확인 API
	@GetMapping("/is-duplicated-nickname")
	public Map<String, Object> isDuplicatedNickname(
			@RequestParam("nickname") String nickname) {
		
		// DB select
		UserEntity user = userBO.getUserEntityByNickname(nickname);
				
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 중복O
			result.put("code", 200);
			result.put("is_duplicated_nickname", true);
		} else {
			result.put("code", 200);
			result.put("is_duplicated_nickname", false);
		}
				
		return result;
	}
}
