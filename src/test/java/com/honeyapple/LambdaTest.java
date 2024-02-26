package com.honeyapple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.ToString;
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
	
	//@Test
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
	
	//@Test
	void 메소드레퍼런스() {
		List<String> fruits = List.of("apple", "banana", "cherry");
		fruits = fruits
				.stream()
				.map(String::toUpperCase) // List에서 꺼내는 걔는 String. 그 String에 to~ 메소드를 적용하겠다.
				// 메소드 이름만 가져옴. (이렇게 메소드를 "참조" 하는 것이 메소드 레퍼런스)
				.collect(Collectors.toList()); // stream to List
		// 여기까지 하면 람다식2 와 동일한 방식
		
		log.info(fruits.toString());
	}
	
	
	// 람다) 응용하기
	// 테스트 메소드
	@Test
	void 람다_메소드레퍼런스() {
		List<Person> personList = new ArrayList<>();
		personList.add(new Person("서동옥", 31)); // 기본생성자는 없으므로 값 채운 생성자로 세팅한다.
		personList.add(new Person("홍길동", 20));
		
		// 객체 안에 있는 메소드 호출
		personList.forEach(p -> p.printInfo()); // 람다) List를 돌것임. 꺼낸객체 이름은 p. 그 p의 메소드 작동할것임.
		personList.forEach(Person::printInfo); // 메소드 레퍼런스(객체가 안보이지만 위와 동일한 결과)
		// 굳이 꺼낸애를 이름지을 필요 없으니까?
		
		// 객체를 println으로 출력
		personList.forEach(p -> System.out.println(p)); // 람다 (아예 다른 메소드도 부를 수 있음.)
		personList.forEach(System.out::println); // 메소드 레퍼런스. () 필요없이 "메소드명"만 가져오면 된다. 
		
		// 왜 이걸 하는가?
		// ★ => enum에서 이걸 사용하는 경우가 있다.
	}
	
	@ToString // this라고 해도 자동으로 내부 내용물 보여줌
	@AllArgsConstructor // 값 채워넣는 생성자 자동생성
	class Person {
		// 실험용 클래스
		private String name;
		private int age;
		
		public void printInfo() {
			log.info("### " + this); // this: 이 클래스 자체를 뜻함.
		}
	}
}
