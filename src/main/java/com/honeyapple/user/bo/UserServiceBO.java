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
	// input: loginId, password(해싱완료) / output: UserEntity(내부 정보로 로그인 판단)
	public UserEntity signIn(String loginId, String password) {
		
		// 1. input param으로 유저 select
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);
		if (user != null) {
			// user가 존재. -> 로그인
			return user;
		}
		
		// 2. 로그인 실패 -> id는 존재하는지 확인
		user = userBO.getUserEntityByLoginId(loginId);
		if (user == null) {
			// user가 없음. -> loginId부터 잘못됨.
			return null;
		}
		
		// 여기까지 왔으면, password만 틀린 상황. -> user에 password를 비워서 보내자
		user = user.toBuilder()
				.password(null)
				.build();
		return user;
	}
}
