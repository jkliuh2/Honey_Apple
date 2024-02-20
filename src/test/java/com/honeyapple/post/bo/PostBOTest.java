package com.honeyapple.post.bo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostBOTest {
	
	@Autowired
	PostBO postBO;

	//@Test
	void test() {
		fail("Not yet implemented");
	}
	
	
	@Transactional
	//@Test
	void 글생성테스트() {
		postBO.addPost(1, "ssd", "테스트제목", "테스트내용", 10000, 1, null, null, null, null, null);
	}
	
	
	@Transactional
//	@Test
	void 거래상태업데이트테스트() {
		postBO.updatePostByIdStatus(1, "예약중");
	}
	
	@Transactional
	@Test
	void Article가져오기테스트() {
		postBO.getPostListBySellerIdStatusOrderByIdDesc(1, null, "판매중");
	}

}
