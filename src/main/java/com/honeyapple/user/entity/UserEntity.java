package com.honeyapple.user.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true) // 수정을 위한 toBuiler
@Table(name = "user")
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "loginId")
	private String loginId;
	
	private String nickname;
	
	private String password;
	
	private String email;
	
	@Column(name = "profileImagePath")
	private String profileImagePath;
	
	private String hometown;
	
	@Builder.Default
	private Double temperature = 36.5;
	
	private String type;   // 외부 API 로그인 할 경우를 위한 컬럼
	
	@Column(name = "typeId")
	private Long typeId; // 외부 사이트의 회원에 대한 id pk값
	
	@UpdateTimestamp
	@Column(name = "createdAt", updatable = false)
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;
}
