package com.honeyapple.kakao.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.honeyapple.kakao.dto.KakaoUserInfoResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class KakaoUserInfo {
    private final WebClient webClient;
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public KakaoUserInfoResponse getUserInfo(String token) {
        String uri = USER_INFO_URI;

        // 토큰으로 사용자 정보 가져오기 요청
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }
}
