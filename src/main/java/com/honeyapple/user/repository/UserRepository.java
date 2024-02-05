package com.honeyapple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeyapple.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByLoginId(String loginId);
	public UserEntity findByNickname(String nickname);
}
