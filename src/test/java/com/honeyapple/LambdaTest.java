package com.honeyapple;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LambdaTest {

	//@Test
	void 람다테스트1() {
		// 1.람다식으로 List의 값들중 특정값을 뽑아내기
		List<String> list = List.of("apple", "banana", "cherry");
		list
		.stream() // 계속 줄줄이 이어지다. -> 엄연히 말하면 나오는 값이 String은 아님(뭔가에 감싸져 있음)
		.filter(fruit -> fruit.startsWith("b")) // 하나하나 뽑아낸 값을 fruit라 할 것이고, b로 시작해야 뽑을 것이다.
		.forEach(fruit -> log.info("### {}", fruit)); // 뽑은 걔를 log찍을 것이다.
	}
	
	@Test
	void 람다테스트2() {
		// 2. List의 값들을 모두 대문자로 변환하기.
		List<String> list = List.of("apple", "banana", "cherry");
		list = list
		.stream()
		.map(fruit -> fruit.toUpperCase()) // 일단 이름은 fruit. 걔들을 대문자로 변환할 것임. 
		.collect(Collectors.toList()); // 대문자로 변환한 값들을 새로운 collection으로 묶을 때 쓰는 메소드
		// Collectors.toList() : stream to List(stream -> List 변환)
		
		// 위에서 list를 대문자로 모두 변환한 다음 저장했음.
		log.info(list.toString()); // [APPLE, BANANA, CHERRY]
	}
}
