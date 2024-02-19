package com.honeyapple.review.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {

	// insert
	public void insertReview(
			@Param("postId") int postId,
			@Param("buyerId") int buyerId,
			@Param("sellerId") int sellerId,
			@Param("score") int score,
			@Param("review") String review);
}
