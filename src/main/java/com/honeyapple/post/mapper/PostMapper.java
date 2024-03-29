package com.honeyapple.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.honeyapple.post.domain.Post;
import com.honeyapple.user.entity.UserEntity;

@Mapper
public interface PostMapper {

	// insert
	public void insertPost(Post post);
	
	// select by postId
	public Post selectPostById(int postId);
	
	// select(판매중+최신+6개)
	public List<Post> selectPostListByStatusOrderByIdDescLimit6(String status);
	
	// select(sellerId+status(or exceptStatus)+id내림차순)
	public List<Post> selectPostListBySellerIdStatusOrderByIdDesc(
			@Param("sellerId") int sellerId,
			@Param("status") String status,
			@Param("exceptStatus") String exceptStatus);
	
	// select(keyword + userList) (둘 모두 NULL 가능)
	public List<Post> selectPostListByKewordAndUserList(
			@Param("keyword") String keyword, 
			@Param("userList") List<UserEntity> userList);
	
	// update(거래상태 update)
	public void updatePostByIdStatus(
			@Param("postId") int postId,
			@Param("status") String status);
	// update(글 수정)
	public void updatePost(Post post);
}
