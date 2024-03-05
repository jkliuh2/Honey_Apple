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
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.aop.TimeTrace;
import com.honeyapple.common.EncryptUtils;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.domain.User;
import com.honeyapple.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
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
			@RequestParam(name = "sido", required = true) Integer sido,
			@RequestParam(name = "sigugun", required = true) Integer sigugun,
			@RequestParam(name = "dong", required = true) Integer dong,
			HttpSession session
			) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 비밀번호 hashing
		String hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		
		// DB insert
		UserEntity user = userBO.addUser(loginId, nickname, hashedPassword, email,
				sido, sigugun, dong);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 회원가입 성공
			result.put("code", 200);
			
			// 로그인 처리
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
			session.setAttribute("userProfileImagePath", user.getProfileImagePath());
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
	@TimeTrace
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
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
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
			session.setAttribute("userProfileImagePath", user.getProfileImagePath());
		}
		
		return result;
	}
	
	
	// 본인인증 API
	@PostMapping("/identity-verification")
	public Map<String, Object> identityVerification(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Map<String, Object> result = new HashMap<>();
		
		// 세션 상태 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 400);
			result.put("error_message", "세션이 만료되었습니다.");
			return result;
		}
		
		// 비밀번호 암호화
		String hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		
		// 입력한 loginId+password로 user 가져오기
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		if (user == null || user.getId() != userId) {
			// 아이디, 비번이 틀림 or 다른 사람것을 가져옴.
			result.put("code", 300);
			result.put("error_message", "로그인 정보가 잘못되었습니다.");
			return result;
		}
		
		// 토큰 발급
		
		
		// 응답
		result.put("code", 200);
		return result;
	}
	
	
	// 유저 정보 수정 API
	@PostMapping("/update")
	public Map<String, Object> update(
			@RequestParam("nickname") String nickname,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "profileImgFile", required = false) MultipartFile profileImgFile,
			@RequestParam("emptyProfile") boolean emptyProfile,
			HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 비밀번호 null처리 및 암호화 // 잘 안되는중
		String hashedPassword = null;
		if (!password.equals("")) {
			hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		}
		
		// 세션에서 로그인정보 가져오기
		int userId = (int)session.getAttribute("userId");
		
		// BO로 전달 -> 수정된 user 정보 리턴
		User user = userBO.updateUser(userId, nickname, hashedPassword, profileImgFile, emptyProfile);
		
		// 기존 세션의 유저정보 삭제
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userNickname");
		session.removeAttribute("userProfileImagePath");
		// 새로운 유저 정보로 다시 저장
		session.setAttribute("userId", user.getId());
		session.setAttribute("userLoginId", user.getLoginId());
		session.setAttribute("userNickname", user.getNickname());
		session.setAttribute("userProfileImagePath", user.getProfileImagePath());
		
		// 응답
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	}
}
