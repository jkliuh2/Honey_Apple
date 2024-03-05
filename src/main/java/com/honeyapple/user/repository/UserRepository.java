package com.honeyapple.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeyapple.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByLoginId(String loginId);
	public UserEntity findByNickname(String nickname);
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
	public UserEntity findByTypeAndTypeId(String type, Long typeId);
	
	public List<UserEntity> findByHometownStartingWith(String hometown);
}
