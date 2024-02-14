package com.honeyapple.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.honeyapple.post.domain.Post;

@Mapper
public interface PostMapper {

	// insert
	public void insertPost(Post post);
	
	// select by postId
	public Post selectPostById(int postId);
	
	// select(판매중+최신+6개)
	public List<Post> selectPostListByStatusOrderByIdDescLimit6(String status);
}
