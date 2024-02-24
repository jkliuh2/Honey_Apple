package com.honeyapple.article.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.article.domain.Article;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.interest.bo.InterestBO;
import com.honeyapple.interest.domain.Interest;
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
	
	
	// 판매자 기준) sellerId + Status로 게시글 전부 가져오기
	public List<Article> getArticleListBySellerIdStatus(int sellerId, String status, String exceptStatus) {
		// 필요정보:post, seller, 관심count
		UserEntity seller = userBO.getUserEntityById(sellerId);
		
		List<Article> articleList = new ArrayList<>();
		
		// article 가져오기(status, exceptStatus 둘 중 하나는 null)
		List<Post> postList = postBO.getPostListBySellerIdStatusOrderByIdDesc(sellerId, status, exceptStatus);
		for (Post post : postList) {
			Article article = new Article();
			article.setPost(post);
			article.setUser(seller);
			article.setInterestCount(interestBO.getInterestCountByPostId(post.getId()));
			articleList.add(article);
		}
		return articleList;
	}
	
	// 구매자 기준) buyerId + 관심등록한 글 List 가져오기(관심등록시간 기준 최신정렬)
	public List<Article> getHasInterestArticleListByBuyerId(int buyerId) {
		List<Article> articleList = new ArrayList<>();
		
		// buyerId가 등록한 관심List(최신순 정렬) 가져오기
		List<Interest> interestList = interestBO.getInterestListByBuyerIdSortByRecentest(buyerId);
		
		// interest의 postId로 article 세팅하기
		for (Interest interest : interestList) {
			Article article = new Article();
			
			// post
			Post post = postBO.getPostById(interest.getPostId());
			article.setPost(post);
			
			// user(seller)
			UserEntity user = userBO.getUserEntityById(post.getSellerId());
			article.setUser(user);
			
			// 관심Count
			int interestCount = interestBO.getInterestCountByPostId(post.getId());
			article.setInterestCount(interestCount);
			
			// List에 넣기
			articleList.add(article);
		}
		
		return articleList;
	}
}
