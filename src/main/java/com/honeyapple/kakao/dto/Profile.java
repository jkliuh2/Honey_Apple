package com.honeyapple.kakao.dto;

import lombok.Data;

@Data
public class Profile {
	private String nickname; // 닉네임
	private String thumbnail_image_url; // 프로필 사진 미리보기(작은 사이즈)
	private String profile_image_url; // 큰사이즈
	
	private Boolean is_default_image; // 위의 url이 기본 url인지 여부
	// T - 기본 / F - 사용자 등록 사진
}
