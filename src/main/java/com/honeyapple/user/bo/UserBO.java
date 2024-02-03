package com.honeyapple.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.user.entity.UserEntity;
import com.honeyapple.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;

	// 회원가입
	// input: 4개 param / output: UserEntity(가입한 정보 반환)
	public UserEntity addUser(
			String loginId, String nickname, 
			String password, String email) {
		
		// 회원 type 설정
		String type = "HoneyApple";
		
		// builder
		UserEntity userEntity = userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.nickname(nickname)
				.password(password)
				.email(email)
				.type(type)
				.build());
		
		// insert + return
		return userEntity == null ? null : userEntity;
	}
}
