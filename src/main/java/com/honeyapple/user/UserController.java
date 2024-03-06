package com.honeyapple.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
// view 컨트롤러
	
	@Autowired
	private UserBO userBO;
	

	/**
	 * 회원가입 view
	 * http://localhost/user/sign-up-view
	 * 비-로그인 상태 only
	 * @param model
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/signUp");
		model.addAttribute("titleName", "회원가입");
		return "template/layout";
	}
	
	
	/**
	 * 로그인 view
	 * http://localhost/user/sign-in-view
	 * 비-로그인 상태 only
	 * @param model
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/signIn");
		model.addAttribute("titleName", "로그인");
		return "template/layout";
	}
	
	
	/**
	 * 로그아웃
	 * 필터 예외처리 OK
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userNickname");
		session.removeAttribute("userProfileImagePath");
		return "redirect:/user/sign-in-view";
	}
	
	
	// 본인인증 페이지 view(필터처리X, 비-로그인처리 필요)
	@GetMapping("/identity-verification-view")
	public String identityVerificationView(
			HttpSession session,
			Model model) {
		
		// 비-로그인 처리
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		// 토큰 select -> 토큰존재할 경우, 유저 정보 수정 페이지로 리다이렉트
		
		
		// 응답
		model.addAttribute("viewName", "user/identityVerification");
		model.addAttribute("titleName", "본인인증");
		return "template/layout";
	}
	
	
	// 유저정보 수정 페이지
	@GetMapping("/update-view")
	public String updateView(HttpSession session, Model model) {
		
		// 세션 정보 가져오기 + 비-로그인 처리
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		// 토큰 확인(본인인증)
		
		// 유저 정보 select
		UserEntity user = userBO.getUserEntityById(userId);
		
		// 응답
		model.addAttribute("user", user);
		model.addAttribute("viewName", "user/update");
		model.addAttribute("titleName", "정보수정");
		return "template/layout";
	}
	
	
	// 유저 동네설정 페이지
	@GetMapping("/set-hometown-view")
	public String setHometownView(HttpSession session, Model model) {
		
		model.addAttribute("viewName", "user/setHometown");
		model.addAttribute("titleName", "우리 동네 설정하기");
		return "template/layout";
	}
}
