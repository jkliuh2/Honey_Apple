package com.honeyapple.kakao.util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.honeyapple.kakao.dto.KakaoTokenResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class KakaoTokenJsonData {
// 인가코드를 이용, Token (Access, Refresh)를 받는다.
	
	private final WebClient webClient;
	private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
	private static final String REDIRECT_URI = "http://43.201.66.155:8080/oauth";
	private static final String GRANT_TYPE = "authorization_code";
	private static final String CLIENT_ID = "e69c8cb6c6982e89cceb6b10f29ea504"; // Rest API 키
	
	public KakaoTokenResponse getToken(String code) {
		String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
		System.out.println(uri);
		
		// 인가 코드로 토큰발급 요청 보내기. (response == 토큰)
		Flux<KakaoTokenResponse> response = webClient.post()
				.uri(uri)
				.header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(KakaoTokenResponse.class);

		return response.blockFirst();
	}
}
