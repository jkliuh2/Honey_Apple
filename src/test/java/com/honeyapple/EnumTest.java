package com.honeyapple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.Getter;

public class EnumTest {

	@Getter // getter 어노테이션. setter는 밑에서 해줘서 없어도 된다.
	public enum Status {
		// 원래 이렇게 만들지 않고, Enum으로 만드는게 정석.
		
		// 열거형 정의
//		Y,
//		N
//		;
		
		// 열거형 정의(추가)
		Y(1, true),
		N(0, false)
		;
		// 값을 추가하면 에러 -> Y, N이 일종의 생성자 취급. 해당 값이 들어갈 필드+생성자가 필요하다.
		
		// enum에 정의된 항목의 필드
		private int value1; // 1/0 이 들어가는 필드1
		private boolean value2; // T/F 가 들어가는 필드2
		
		// 생성자 (enum 생성자는 앞에 붙는 잡다한것들을 생략해도 무관하다.)
		Status(int value1, boolean value2) { 
			this.value1 = value1;
			this.value2 = value2;
		}
	}
	
	
	// enum 테스트 하기
	@Test
	void status테스트() {
		// given - 준비
		Status status = Status.Y; // Y를 가져옴
		
		// when - 실행
		int value1 = status.getValue1();
		boolean value2 = status.isValue2(); // ★ boolean 타입은 get이 아닌 is로 꺼낸다.
		// 1, true가 들어있을 것이다. -> 이를 검증한다
		
		// then - 검증
		assertEquals(status, Status.Y); // 초록색 뜨면 두 파라미터가 같다는 뜻
		assertEquals(1, value1);
		assertEquals(true, value2);
	}
}
