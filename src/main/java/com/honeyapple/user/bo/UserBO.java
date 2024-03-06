package com.honeyapple.user.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.common.FileManagerService;
import com.honeyapple.kakao.dto.KakaoAccount;
import com.honeyapple.user.domain.User;
import com.honeyapple.user.entity.UserEntity;
import com.honeyapple.user.mapper.UserMapper;
import com.honeyapple.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserBO {
	
	@Autowired
	private HometownBO hometownBO;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileManagerService fileManagerService;

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
	
	public List<UserEntity> getUserEntityByHometownStartingWith(String hometown) {
		return userRepository.findByHometownStartingWith(hometown);
	}

	////////////////////////////////////////////////////////// API 관련 메소드들
	
	// 카카오 회원가입
	public UserEntity signKakao(Long typeId, KakaoAccount kakaoAccount) {
		// 1. 유저가 존재하는 지 확인, 존재하면 해당 유저정보 리턴
		UserEntity user = userRepository.findByTypeAndTypeId("카카오", typeId);
		if (user != null) {
			return user;
		}
		
		// 2. 유저가 존재하지 않으면, 회원가입 후 리턴.
		// 로그인 아이디 랜덤으로 설정하기(로그인 아이디는 필요함)
		String loginId = null;
		while (true) {
			loginId = "kakao_" + ((int)(Math.random() * 999999) + 1);
			if (getUserEntityByLoginId(loginId) == null) {
				break;
			}
		}
		
		// 랜덤 loginId, 닉네임(카카오) + 프로필 사진(카카오)으로 회원가입
		user = UserEntity.builder()
				.loginId(loginId)
				.nickname("kakao_" + kakaoAccount.getProfile().getNickname())
				.profileImagePath(kakaoAccount.getProfile_image_needs_agreement() ? 
								kakaoAccount.getProfile().getThumbnail_image_url() : null)
				.type("카카오") // 타입: 카카오
				.typeId(typeId) // 카카오 계정 확인용 ID값
				.build();
		
		return userRepository.save(user);
	}

	// 회원가입
	// input: 4개 param / output: UserEntity(가입한 정보 반환)
	public UserEntity addUser(String loginId, String nickname, String password, String email,
			Integer sido, Integer sigugun, Integer dong) {

		// 회원 type 설정 (수정예정)
		String type = "HoneyApple";
		
		// 주소 합치기
		String hometown = hometownBO.codeMerge(sido, sigugun, dong);

		// builder
		UserEntity userEntity = userRepository.save(UserEntity.builder().loginId(loginId).nickname(nickname)
				.password(password).email(email).type(type).hometown(hometown).build());

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
	public User updateUser(int userId, String nickname, String password, 
			MultipartFile profileImgFile, boolean emptyProfile) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		
		// File 업로드 및 Path 변환
		String profileImagePath = null;
		if (profileImgFile != null) {
			profileImagePath = fileManagerService.saveFile(user.getLoginId(), profileImgFile);
		}
		
		// emptyProfile T/F 여부에 따라 업데이트 + 기존 이미지 삭제처리
		// 1. emptyProfile == true (무조건 ImgFile은 null로 들어옴.)
		// ImgPath를 NULL로 변경(무조건 기존 이미지 존재하므로 삭제처리 해야 한다.
		// 2. emptyProfile == false
		// ImgFile이 존재하면 변경. null이면 유지.
		
		// update - mapper에서 if문으로 처리
		userMapper.updateUser(userId, nickname, password, 
				profileImagePath, emptyProfile); 
		
		// 기존 이미지 삭제 -> 조건 모두 합쳐서 정리
		if ((emptyProfile || profileImgFile != null) 
				&& user.getProfileImagePath() != null) {
			fileManagerService.deleteFile(user.getProfileImagePath());
		} 
		
		// 업데이트로 변경된 user 정보 리턴하기
		return userMapper.selectUserById(userId);
	}
	
	
	// 유저 hometown 수정
	public void updateUserHometown(int userId, int sido, int sigugun, int dong) {
		// 수정할 유저 데이터 select
		UserEntity user = getUserEntityById(userId);
		
		// parameter로 들어온 세개의 주소코드값 하나로 merge
		String hometown = hometownBO.codeMerge(sido, sigugun, dong);
		
		userRepository.save(user.toBuilder()
				.hometown(hometown)
				.build());
	}
}
