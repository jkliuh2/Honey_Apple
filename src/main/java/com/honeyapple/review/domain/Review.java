package com.honeyapple.review.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Review {
	private int postId;
	private int buyerId;
	private int sellerId;
	private int score;
	private String review;
	private Date createdAt;
}
