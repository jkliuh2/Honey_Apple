package com.honeyapple.post.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Post {
	private int id;
	private int	sellerId;
	private String subject;
	private int	price;
	private int	negotiable; // 0or1
	private String content;
	private String imgPath1;
	private String imgPath2;
	private String imgPath3;
	private String imgPath4;
	private String imgPath5;
	private String status;   // 판매중 / 예약중 / 판매완료
	private Date createdAt;
	private Date updatedAt;
}
