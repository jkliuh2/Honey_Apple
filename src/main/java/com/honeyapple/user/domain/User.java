package com.honeyapple.user.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
	private int id;
	private String loginId;
	private String nickname;
	private String password;
	private String email;
	private String profileImagePath;
	private String hometown;
	private Double temperature = 36.5;
	private String type; 
	private Long typeId;
	private Date createdAt;
	private Date updatedAt;
}
