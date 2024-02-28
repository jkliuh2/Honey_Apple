package com.honeyapple.article.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.article.domain.Article;
import com.honeyapple.chat.bo.ChatBO;
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
	private ChatBO chatBO;
	
	@Autowired
	private InterestBO interestBO;

	
/////////////////////////////////////////////// 
	
	// 검색 메소드 // 유저 동네정보 설정하고 바꿀것임.
	public List<Article> searchArticle(String keyword, 
			Integer sido, Integer sigugun, Integer dong, boolean juso) {
		
		// 주소 3개를 조합해서 8자의 String으로 만든다.
		String hometown = "";
		
		
		return null;
	}
	
	
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
	
	// my-trade에서의 Article 가져오는 메소드
	// input: buyerId, menu / output: List<Article>
	// ★ 나중에 menu도 enum으로 설정해야하듯.
	public List<Article> getMyTradeArticleListByBuyerId(
			int buyerId, String menu) {
		// 최종 리턴할 ArticleList
		List<Article> articleList = new ArrayList<>();
		// article 하나와 1:1 대응하는 post의 List
		List<Post> postList = new ArrayList<>(); 
		
		// 1. menu에 따른 postList 설정
		if (menu == null) { // 기본페이지(관심등록 List)
			// buyerId가 등록한 관심List(최신순 정렬) 가져오기
			List<Interest> interestList = interestBO.getInterestListByBuyerIdSortByRecentest(buyerId);
			
			// interest의 postId로 postList 세팅하기
			for (Interest interest : interestList) {
				// post 설정
				Post post = postBO.getPostById(interest.getPostId());
				postList.add(post);
			}
		} else if (menu.equals("wantToBuyList")) { // 거래제안글(채팅방 존재 글)
			// buyerId로 채팅방 리스트(updatedAt 최신순 정렬) 가져오기
			List<ChatEntity> chatList = chatBO.getChatEntityListByBuyerIdDesc(buyerId);
			
			// chat의 postId로 postList 세팅하기
			for (ChatEntity chat : chatList) {
				// post 설정
				Post post = postBO.getPostById(chat.getPostId());
				postList.add(post);
			}
		} else if (menu.equals("reservationList")) { // 예약중인 글
			// buyerId + "예약" 상태 채팅방 리스트(updatedAt 최신순 정렬)
			List<ChatEntity> chatList = chatBO.getChatEntityListByBuyerIdTradeStatusDesc(buyerId, "예약");
			
			// chat의 postId로 postList 세팅하기
			for (ChatEntity chat : chatList) {
				// post 설정
				Post post = postBO.getPostById(chat.getPostId());
				postList.add(post);
			}
		} else if (menu.equals("purchaseComplete")) { // 구매완료 글
			// buyerId + "완료" 상태 채팅방 리스트(updatedAt 최신순 정렬)
			List<ChatEntity> chatList = chatBO.getChatEntityListByBuyerIdTradeStatusDesc(buyerId, "예약");
			
			// chat의 postId로 postList 세팅하기
			for (ChatEntity chat : chatList) {
				// post 설정
				Post post = postBO.getPostById(chat.getPostId());
				postList.add(post);
			}
		}
		
		
		
		// 2. 위에서 설정한 postList로 article 세팅해서 List에 넣기
		for (Post post : postList) {
			Article article = new Article();
			
			// post
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
