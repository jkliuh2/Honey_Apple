package com.honeyapple.post.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.honeyapple.post.domain.Post;

@Mapper
public interface PostMapper {

	// insert
	public void insertPost(Post post);
	
	// select by postId
	public Post selectPostById(int postId);
}
