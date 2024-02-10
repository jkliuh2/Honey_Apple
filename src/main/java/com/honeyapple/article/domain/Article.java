package com.honeyapple.article.domain;

import com.honeyapple.post.domain.Post;
import com.honeyapple.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Article {
// 게시글 한개에 대한 정보 묶음

	private Post post; // 게시물 1개
	
	private UserEntity user; // 판매자(글쓴이) 정보
	
	private int interestCount; // 관심 숫자
	
	private boolean filledHeart; // 관심 여부(로그인된 유저가 이 게시글을 관심눌렀는? 여부)
	
	// 채팅방 갯수(판매자를 위한 정보)
}
