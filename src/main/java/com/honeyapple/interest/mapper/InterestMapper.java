package com.honeyapple.interest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestMapper {

	// select(count)
	// 관심숫자 세기, 관심여부확인 합친 메소드
	public int selectInterestCountByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	
	// insert
	// input:postId, userId / output:x
	public void insertInterest(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	// delete
	// input:postId, userId / output:x
	public void deleteInterest(
			@Param("postId") int postId,
			@Param("userId") int userId);
}
