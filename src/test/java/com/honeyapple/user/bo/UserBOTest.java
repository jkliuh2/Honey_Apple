package com.honeyapple.user.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.honeyapple.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserBOTest {
	
	@Autowired
	UserBO userBO;

	@Transactional // 롤백해주는 어노테이션.(스프링 프레임워크 import)
	@Test
	void 유저추가테스트() {
		log.info("###### 유저 추가 테스트 시작");
		UserEntity user = userBO.addUser("test2222", "닉네임 테스트2", "비번 테스트2", "이메일 테스트2");
		log.info("유저 온도 : " + user.getTemperature());
	}
}
