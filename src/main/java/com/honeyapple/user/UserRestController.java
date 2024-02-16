package com.honeyapple.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	
	/**
	 * 회원가입 API
	 * 
	 * @param loginId
	 * @param nickname
	 * @param password
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("nickname") String nickname,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			HttpSession session
			) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 비밀번호 hashing
		String hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		
		// DB insert
		UserEntity user = userBO.addUser(loginId, nickname, hashedPassword, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 회원가입 성공
			result.put("code", 200);
			
			// 로그인 처리
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
		} else {
			result.put("code", 500);
			result.put("error_message", "회원정보를 DB에 저장하는데 실패했습니다.");
		}
		return result;
	}
	
	
	/**
	 * 로그인 아이디 중복확인 API  
	 * 
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is-duplicated-loginId")
	public Map<String, Object> isDuplicatedLoginId(
			@RequestParam("loginId") String loginId) {
		
		// BOselect + 응답값
		Map<String, Object> result = new HashMap<>();
		if (userBO.isDuplicatedLoginId(loginId)) {
			// 중복O
			result.put("code", 200);
			result.put("is_duplicated_loginId", true);
		} else {
			// 중복X
			result.put("code", 200);
			result.put("is_duplicated_loginId", false);
		}
		
		return result;
	}
	
	
	/**
	 * 닉네임 중복확인 API   
	 * 
	 * @param nickname
	 * @return
	 */
	@GetMapping("/is-duplicated-nickname")
	public Map<String, Object> isDuplicatedNickname(
			@RequestParam("nickname") String nickname) {
		
		// BOselect + 응답값
		Map<String, Object> result = new HashMap<>();
		
		if (userBO.isDuplicatedNickname(nickname)) {
			// 중복O
			result.put("code", 200);
			result.put("is_duplicated_nickname", true);
		} else {
			// 중복X
			result.put("code", 200);
			result.put("is_duplicated_nickname", false);
		}
				
		return result;
	}
	
	
	/**
	 * 로그인 API
	 * 
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 비밀번호 암호화
		String hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		
		Map<String, Object> result = new HashMap<>();
		
		// DB select + 응답값
		UserEntity user = userBO.signIn(loginId, hashedPassword);
		
		if (user == null) {
			// 1. 아이디부터 틀림
			result.put("code", 500);
			result.put("error_message", "아이디가 존재하지 않습니다..");
			
		} else if (user.getPassword() == null) {
			// 2. 비밀번호만 틀림
			result.put("code", 500);
			result.put("error_message", "비밀번호가 일치하지 않습니다.");
			
		} else {
			// 3. 로그인 성공
			
			// model에 담기 
			result.put("code", 200);
			result.put("login_message", user.getNickname() + "님 환영합니다!");
			
			// 세션에 회원정보 저장
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
		}
		
		return result;
	}
}
