package com.honeyapple.article.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.article.domain.Article;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@Service
public class ArticleBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;

	
/////////////////////////////////////////////// 
	
	// select - Article
	// input: userId(로그인유저, Null 가능), postId / output:Article
	public Article getArticleByPostIdUserId(int postId, Integer userId) {
		Article article = new Article();
		
		// post(게시물 정보) 가져오기
		Post post = postBO.getPostById(postId);
		article.setPost(post);
		
		// user(판매자 정보) 가져오기
		int sellerId = post.getSellerId();
		UserEntity user = userBO.getUserEntityById(sellerId);
		article.setUser(user);
		
		// 관심 Count
		
		// 관심 여부 (하트표시, userId 없으면 무조건 꺼짐상태) 
		
		// 채팅방 갯수
		
		
		return article;
	}
}
