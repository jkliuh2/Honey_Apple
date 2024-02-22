package com.honeyapple.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

	// update
	public void updateUser(
			@Param("userId") int userId,
			@Param("nickname") String nickname, // not null
			@Param("password") String password, // null 허용
			@Param("profileImagePath") String profileImagePath,
			@Param("emptyProfile") boolean emptyProfile);
}
