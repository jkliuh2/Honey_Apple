package com.honeyapple.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.kakao.dto.KakaoTokenResponse;
import com.honeyapple.kakao.dto.KakaoUserInfoResponse;
import com.honeyapple.kakao.util.KakaoTokenJsonData;
import com.honeyapple.kakao.util.KakaoUserInfo;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor // @Autowired 안써도 되는 어노테이션
@Controller
@Slf4j
public class KakaoController {
	private final KakaoTokenJsonData kakaoTokenJsonData;
	private final KakaoUserInfo kakaoUserInfo;
	
	@Autowired
	private UserBO userBO;

	/**
	 * 카카오 로그인에 성공하면, 인가코드를 발급하고 함께 보냈던 Redirect uri로 리다이렉트. 되면 여기로 들어온다.
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/oauth")
	public String kakaoOauth(@RequestParam("code") String code, HttpSession session) {
		log.info("$$$$$ 카카오 로그인 1. 받은 인가코드로 토큰 발급");
		KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
		
		log.info("$$$$$ 카카오 로그인 2. 토큰으로 사용자 정보 가져오기 요청");
		KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
		
		log.info("$$$$$ 카카오 로그인 3. 가져온 사용자 정보로 로그인/회원가입 실행");
		UserEntity user = userBO.signKakao(userInfo.getId(), userInfo.getKakao_account());		
		
		log.info("$$$$$ 카카오 로그인 4. 로그인or회원가입이 끝나면 세션에 유저정보를 넣고 메인페이지로 리다이렉트");
		session.setAttribute("userId", user.getId());
		session.setAttribute("userLoginId", user.getLoginId());
		session.setAttribute("userNickname", user.getNickname());
		session.setAttribute("userProfileImagePath", user.getProfileImagePath());
		return "redirect:/honey-apple";
	}
}
