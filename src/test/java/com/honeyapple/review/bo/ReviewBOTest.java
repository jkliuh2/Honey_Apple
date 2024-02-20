package com.honeyapple.review.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewBOTest {
	
	@Autowired
	private ReviewBO reviewBO;

	@Test
	void notnull테스트() {
		reviewBO.getReviewListBySellerIdHasReviewDesc(1);
	}

}
