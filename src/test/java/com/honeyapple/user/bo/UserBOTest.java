package com.honeyapple.user.bo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.honeyapple.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class UserBOTest {
	
	@Autowired
	UserBO userBO;

//	@Transactional
//	@Test
//	void startsWith테스트() {
//		List<UserEntity> userList = userBO.getUserEntityByNicknameStartingWith(null);
//		log.info("$$$$$ userList 가져오기 성공");
//		for (UserEntity user : userList) {
//			log.info("##### 유저 닉네임:" + user.getNickname());
//		}
//		log.info("$$$$$ for문 통과");
//		if (userList.isEmpty()) {
//			log.info("##### 유저없음.");
//		}
//		log.info("$$$$$ 메소드 끝");
//	}
}
