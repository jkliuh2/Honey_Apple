package com.honeyapple.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.user.entity.UserEntity;

@Service
public class UserServiceBO {

	@Autowired
	private UserBO userBO;
	
	
	// loginId 중복확인 API
	public boolean isDuplicatedLoginId(String loginId) {
		// 중복-true / 중복X-false
		
		// DB select - loginId로 user 있는지 확인.
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		return (user != null); 
	}
	
	
	// 닉네임 중복확인 API
	public boolean isDuplicatedNickname(String nickname) {
		// 중복-true / 중복X-false
		
		// DB select - nickname으로 user 있는지 확인.
		UserEntity user = userBO.getUserEntityByNickname(nickname);
		
		return (user != null); 
	}
	
	
	// 로그인 API
}
