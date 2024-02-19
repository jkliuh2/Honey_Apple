package com.honeyapple.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 스프링빈, singleton
@Aspect // 부가 기능 정의(advice) :시간재기 + 어디에 적용?(pointcut)
public class TimeTraceAop {

	// 1) 수행할 패키지 지정 방식
//	@Around("execution(* com.honeyapple..*(..))") // 베이스패키지 하위의 모든 스프링빈에 대해 시간측정 하겠다.
	
	// 2) 어노테이션 지정(내가 새로 어노테이션을 만들고, 그 어노테이션이 붙어있으면 아래의 메소드 적용) -> aop패키지에서 만들것임
	@Around("@annotation(TimeTrace)") // @TimeTrace 라는 어노테이션이 붙어있으면 실행.
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		// 타겟이 적용하는 메소드 - joinPoint
		
		// 시간 측정
		StopWatch stopWatch = new StopWatch(); // 자바내 내장되어있는 시간재는 클래스
		stopWatch.start(); // 시계 시간 시작
		
		// 본래 할 일 수행(예: 회원가입, 로그인, 글쓰기 등)
		Object proceedObj = joinPoint.proceed(); // 에러처리는 throw
		
		// 시계 멈추고 시간 로그로 출력
		stopWatch.stop(); // 시계 멈춤
		log.info("######### 실행 시간(ms): {}", stopWatch.getTotalTimeMillis()); // 출력방법 1
		log.info("$$$$$$$$$ 실행 시간:" + stopWatch.prettyPrint()); // 출력방법 2
		
		return proceedObj;
	}
}
