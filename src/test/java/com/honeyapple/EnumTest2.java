package com.honeyapple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Getter;

public class EnumTest2 {
	
	@Getter // ()안의 title값을 꺼내기 위한 getter
	enum PayMethod { // 아래의 List 내부의 내용들이 타입이 된다고 보면 된다. -> 이걸 PayType에 넣을 것이다.
		// 열거형 정의
		REMITTANCE("무통장입금") // () 없어도 되긴 하지만, 설명을 ""로 넣어주면 알아보기 편하다. 
		, ACCOUNT_TRANSFER("계좌이체")
		, CREDIT("신용카드")
		, NAVER("네이버페이");
		
		// 필드
		private String title; // () 안의 내용을 뜻한다.
		
		// 생성자
		PayMethod(String title) {
			this.title = title;
		}
	}

	enum PayType {
		// 열거형 정의 (현금, 카드, 없음)
//		CASH("현금", List.of("REMITTANCE", "ACCOUNT_TRANSFER"))    // 변경 전 내용들
//		, CARD("카드", List.of("CREDIT", "NAVER"))
//		, EMPTY("없음", Collections.emptyList()); // 실무에서 쓰는 "빈 리스트". Collections.EMPTY_LIST
		// 1번째:String, 2번째:List
		
		CASH("현금", List.of(PayMethod.REMITTANCE, PayMethod.ACCOUNT_TRANSFER))
		, CARD("카드", List.of(PayMethod.CREDIT, PayMethod.NAVER))
		, EMPTY("없음", Collections.emptyList()); // 실무에서 쓰는 "빈 리스트". Collections.EMPTY_LIST
		
		// 필드
		private String title;
		private List<PayMethod> payList;
		
		// 생성자
		PayType(String title, List<PayMethod> payList) {
			this.title = title;
			this.payList = payList;
		}
		
		///////////// 메소드
		// 결제 수단(List 안의 값) String이 enum에 존재하는지 확인
		// (이 메소드를 밖에서 안쓸것이라면 private으로 해도 된다.)
		public boolean hasPayMethod(PayMethod payMethod) {
			return payList.stream() // payList를 순회.
					.anyMatch(pay -> pay.equals(payMethod)); // List의 요소 pay들 중 하나라도 같으면 True
			// List를 순회하면서 parameter와 비교해서 하나라도 일치하면 T를 리턴한다.
			// 애초에 이 메소드는 어떤 타입인지를 이미 알고있는 상황이어야 한다.
		}
		
		// 결제 수단으로 enum 타입 찾기
		// 자식값(리스트 안의 값들)을 받았을 때 부모(CASH, CARD를 리턴하는 메소드)
		public static PayType findByPayMethod(PayMethod payMethod) {
			// static: 생성자 안만들고 하는 메소드
			// PayType: 리턴값이 부모타입
			
			return Arrays.stream(PayType.values()) // PayType의 열거형 변수들을 stream으로 변환(다 꺼냄)
					.filter(payType -> payType.hasPayMethod(payMethod)) // payType: 부모타입 하나하나
					// payMethod와 같은 type을 뽑아냄.(True면 "그 부모타입이 걸러져 나옴")
					
					.findAny() // 그 찾은 요소중에 "아무거나" 반환. (First도 있음)
					.orElse(EMPTY); // 찾아낸 것이 없으면 -> EMPTY로 리턴한다.
		}
	}
	
//	@Test
//	void pay테스트() {
//		// given 준비
//		String payMethod = "NAVER";
//		
//		// when 실행
//		// 결제수단에 해당하는 결제 종류는?
//		PayType payType = PayType.findByPayMethod(payMethod);
//		
//		// then 확인
//		assertEquals(payType, PayType.CARD); // T여야 함
//	}
	
	@Test
	void pay테스트2() {
		// given
		PayMethod payMethod = PayMethod.ACCOUNT_TRANSFER;
		
		// when
		// payMethod라는 결제방법을 가지는 PayType은 무엇인가? -> 결과는 payType에 저장.
		PayType payType = PayType.findByPayMethod(payMethod);
		
		// then
		assertEquals(payType, PayType.CASH); // CASH면 정답. 그외는 오답
		
		// payMethod에 들어가있는 title값(한글) 꺼내기
		assertEquals("계좌이체", payMethod.getTitle()); // 정답
	}
}
