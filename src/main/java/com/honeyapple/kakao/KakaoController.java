package com.honeyapple.kakao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honeyapple.kakao.dto.KakaoTokenResponse;
import com.honeyapple.kakao.dto.KakaoUserInfoResponse;
import com.honeyapple.kakao.util.KakaoTokenJsonData;
import com.honeyapple.kakao.util.KakaoUserInfo;
import com.honeyapple.user.bo.UserBO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor // @Autowired 안써도 되는 어노테이션
@Controller
@Slf4j
public class KakaoController {
	private final KakaoTokenJsonData kakaoTokenJsonData;
	private final KakaoUserInfo kakaoUserInfo;
	
	private UserBO userBO;

	/**
	 * 카카오 로그인에 성공하면, 인가코드를 발급하고 함께 보냈던 Redirect uri로 리다이렉트. 되면 여기로 들어온다.
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/oauth")
	@ResponseBody
	public String kakaoOauth(@RequestParam("code") String code) {
		log.info("$$$$$ 카카오 로그인 1. 받은 인가코드로 토큰 발급");
		KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
		
		log.info("$$$$$ 카카오 로그인 2. 토큰으로 사용자 정보 가져오기 요청");
		KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
		
		log.info("$$$$$ 카카오 로그인 3. 가져온 사용자 정보로 회원가입 실행");
		userBO.addUserKakao(userInfo.getKakao_account());		
		return "okay";
	}
}
