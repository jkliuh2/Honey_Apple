package com.honeyapple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class EnumTest1 {

	public enum CalcType {
		// 열거형 정의
		CALC_A(value -> value), // 람다 문법. input -> output
		CALC_B(value -> value * 10),
		CALC_C(value -> value * 3),
		CALC_ETC(value -> 0);
		
		// ★ enum에 정의된 function(java 제공) - import: java.util.function.Function
		// <input의 타입, output의 타입>
		private Function<Integer, Integer> expression;
		
		// 생성자
		// 이름 : enum의 이름 그대로 가져오면 됨.
		// expression으로 들어오는 것은 위의 정의와 매칭할 것이라는 뜻
		CalcType(Function<Integer, Integer> expression) {
			this.expression = expression;
		}
		
		// 계산 적용 메소드
		// value -> value 가 하나의 메소드인데, 이것을 받는 애가 필요하다.
		public int calculate(int value) {
			return expression.apply(value); // apply(): 제공되는 메소드
			// 위의 CALC_~~ 들을 실행시켜 주는 것이 apply 라고 한다.
		}
	}
	
	// 테스트
	@Test
	void cal테스트() {
		// given(준비)
		CalcType calcType = CalcType.CALC_C; // CALC_C로 타입을 설정.(* 3)
		
		// when(실행)
		int result = calcType.calculate(500); // 500을 input -> 1500이 결과에 들어갈 것.
		
		// then(검증)
		assertEquals(1500, result); // result == 1500 이면 초록색.
	}
}
