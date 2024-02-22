package com.honeyapple.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.user.entity.UserEntity;
import com.honeyapple.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;

	///////////////////////////////////////////////////// 간단한 메소드들
	// loginId로 select
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}

	// 닉네임으로 select
	public UserEntity getUserEntityByNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}

	// loginId + 비밀번호 select
	// input: loginId, password(hashing) / output:UserEntity
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}

	// id로 select
	public UserEntity getUserEntityById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	////////////////////////////////////////////////////////// API 관련 메소드들

	// 회원가입
	// input: 4개 param / output: UserEntity(가입한 정보 반환)
	public UserEntity addUser(String loginId, String nickname, String password, String email) {

		// 회원 type 설정
		String type = "HoneyApple";

		// builder
		UserEntity userEntity = userRepository.save(UserEntity.builder().loginId(loginId).nickname(nickname)
				.password(password).email(email).type(type).build());

		// insert + return
		return userEntity == null ? null : userEntity;
	}

	// loginId 중복확인 API
	public boolean isDuplicatedLoginId(String loginId) {
		// 중복-true / 중복X-false

		// DB select - loginId로 user 있는지 확인.
		UserEntity user = getUserEntityByLoginId(loginId);

		return (user != null);
	}

	// 닉네임 중복확인 API
	public boolean isDuplicatedNickname(String nickname) {
		// 중복-true / 중복X-false

		// DB select - nickname으로 user 있는지 확인.
		UserEntity user = getUserEntityByNickname(nickname);

		return (user != null);
	}

	// 로그인 API
	// input: loginId, password(해싱완료) / output: UserEntity(내부 정보로 로그인 판단)
	public UserEntity signIn(String loginId, String password) {

		// 1. input param으로 유저 select
		UserEntity user = getUserEntityByLoginIdPassword(loginId, password);
		if (user != null) {
			// user가 존재. -> 로그인
			return user;
		}

		// 2. 로그인 실패 -> id는 존재하는지 확인
		user = getUserEntityByLoginId(loginId);
		if (user == null) {
			// user가 없음. -> loginId부터 잘못됨.
			return null;
		}

		// 여기까지 왔으면, password만 틀린 상황. -> user에 password를 비워서 보내자
		user = user.toBuilder().password(null).build();
		return user;
	}
	
	
	//////////////////////////////////////////////////
	
	// 유저 정보 수정 
	public void updateUser(int userId, String nickname, String password, 
			MultipartFile profileImgFile, boolean emptyProfile) {
		
	}
}
