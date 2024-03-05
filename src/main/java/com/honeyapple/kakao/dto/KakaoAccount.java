package com.honeyapple.kakao.dto;

import lombok.Data;

@Data
public class KakaoAccount {
	private Boolean profile_needs_agreement; // 프로필 정보 제공가능 동의
	private Boolean profile_nickname_needs_agreement; // 닉네임 제공가능 동의
	private Boolean profile_image_needs_agreement; // 프로필 사진 제공가능 동의
	private Profile profile; // 프로필 정보(닉네임, 프로필 사진)
}
