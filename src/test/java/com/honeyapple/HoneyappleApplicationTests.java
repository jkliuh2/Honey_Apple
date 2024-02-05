package com.honeyapple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class HoneyappleApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Test
	void 더하기테스트() {
		log.info("$$$$$$ 더하기 테스트 시작.");
		int a = 10;
		int b = 20;
		
		assertEquals(25, a + b); // 30, a+b가 맞으면 초록색, 틀리면 빨간색
	}
}
