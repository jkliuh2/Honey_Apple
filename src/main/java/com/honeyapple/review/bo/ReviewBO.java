package com.honeyapple.review.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.review.domain.Review;
import com.honeyapple.review.mapper.ReviewMapper;

@Service
public class ReviewBO {
	
	@Autowired
	private ReviewMapper reviewMapper;

	
	// insert
	public void addReview(int postId, int buyerId, int sellerId,
			int score, String review) {
		reviewMapper.insertReview(postId, buyerId, sellerId, score, review);
	}
	
	// select) sellerId의 모든 리뷰(최신순)
	public List<Review> getReviewListBySellerIdHasReviewDesc(int sellerId) {
		return reviewMapper.selectReviewListBySellerIdHasReviewDesc(sellerId);
	}
}
