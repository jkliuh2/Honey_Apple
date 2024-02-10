package com.honeyapple.interest.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Interest {
	private int postId;
	private int userId;
	private Date createdAt;
}
