package com.honeyapple.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.honeyapple.review.domain.Review;

@Mapper
public interface ReviewMapper {

	// insert
	public void insertReview(
			@Param("postId") int postId,
			@Param("buyerId") int buyerId,
			@Param("sellerId") int sellerId,
			@Param("score") int score,
			@Param("review") String review);
	
	// select
	public List<Review> selectReviewListBySellerIdHasReviewDesc(int sellerId);
}
