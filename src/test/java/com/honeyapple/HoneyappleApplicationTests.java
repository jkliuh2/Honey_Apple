package com.honeyapple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class HoneyappleApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	//@Test
	void 더하기테스트() {
		log.info("$$$$$$ 더하기 테스트 시작.");
		int a = 10;
		int b = 20;
		
		assertEquals(25, a + b); // 30, a+b가 맞으면 초록색, 틀리면 빨간색
	}
	
	
	@Test
	void 비어있는지확인() {
		List<Integer> list = new ArrayList<>(); // []
		if (ObjectUtils.isEmpty(list)) {
			// 이것으로 NPE 오류를 막을 수 있다.(빈 Array이든, 아예 빈 공간이든)
			log.info("list is empty");
		}
		
//		String str = null;
		String str = "";
		if (ObjectUtils.isEmpty(str)) {
			log.info("str is empty");
		}
	}
}
