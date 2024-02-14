package com.honeyapple.article.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.article.domain.Article;
import com.honeyapple.interest.bo.InterestBO;
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
	
	@Autowired
	private InterestBO interestBO;

	
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
		int interestCount = interestBO.getInterestCountByPostId(postId);
		article.setInterestCount(interestCount);
		
		// 관심 여부 (하트표시, userId 없으면 무조건 꺼짐상태) 
		boolean filledHeart = false;
		if (userId != null) {
			filledHeart = interestBO.filledHeart(postId, userId);
		}
		article.setFilledHeart(filledHeart);
		
		// 채팅방 갯수
		
		
		return article;
	}
	
	
	// 메인페이지 List<Article> select
	public List<Article> getArticleList() {
		// 필요 정보: post, user, 관심Count
		// post.status == "판매중" 인 것만 가져와야 한다. + id 최신 + limit 6
		
		List<Article> articleList = new ArrayList<>();
		
		List<Post> postList = postBO.getPostListByStatusOrderByIdDescLimit6("판매중");
		for (Post post : postList) {
			Article article = new Article();
			
			// post
			article.setPost(post);
			
			// user(판매자)
			article.setUser(userBO.getUserEntityById(post.getSellerId()));
			
			// 관심Count
			article.setInterestCount(interestBO.getInterestCountByPostId(post.getId()));
			
			articleList.add(article);
		}
		
		return articleList;
	}
}
