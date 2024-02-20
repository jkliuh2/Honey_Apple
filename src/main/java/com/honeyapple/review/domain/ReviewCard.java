package com.honeyapple.review.domain;

import com.honeyapple.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ReviewCard {
// review + buyer(리뷰 쓴 사람)
	private Review review;
	
	private UserEntity buyer;
}
