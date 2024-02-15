package com.honeyapple.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그찍기용
@Component
public class TestBatch {

	@Scheduled(cron = "0 */1 * * * *") // 스케줄러 등록을 위한 어노테이션 (cron tab의 문법 사용)
	public void task() {
		// 특정 시간마다 이 메소드의 작업을 수행할 것임.
		log.info("############ batch 수행");
	}
}
