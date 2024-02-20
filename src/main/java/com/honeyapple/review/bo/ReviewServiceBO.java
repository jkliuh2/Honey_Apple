package com.honeyapple.review.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chat.bo.ChatMessageBO;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;
import com.honeyapple.review.domain.Review;
import com.honeyapple.review.domain.ReviewCard;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@Service
public class ReviewServiceBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	@Autowired
	private ReviewBO reviewBO;

	
	// 거래완료 API
	// output: 1-잘 처리됨. / 2-로그인정보가 구매자가 아님.
	public int completeTrade(int userId, int chatId, int score, String review) {
		// userId가 chatId의 구매자인지 확인
		ChatEntity chat = chatBO.getChatEntityByChatId(chatId);
		Post post = postBO.getPostById(chat.getPostId());
		
		if (chat.getBuyerId() != userId) {
			return 2;
		}
		
		// review insert
		reviewBO.addReview(post.getId(), userId, post.getSellerId(), score, review);
		
		// post.status -> "판매완료"
		postBO.updatePostByIdStatus(post.getId(), "판매완료");
		
		// chat.tradeStatus -> "완료"
		chatBO.updateChatByIdTradeStatus(chatId, "완료");
		
		// 해당 채팅방에 chatMessage-거래완료 추가
		chatMessageBO.enterStatusMessage(chatId, "거래완료");
		
		// 매너온도 변경 - 이건 아직 어떻게 할지 모름.
		
		// 리턴
		return 1;
	}
	
	// select) sellerId의 유저의 모든 리뷰들
	public List<ReviewCard> getReviewCardListBySellerId(int sellerId) {
		// 1. Review, 2. buyer(구매자)
		List<ReviewCard> reviewCardList = new ArrayList<ReviewCard>();
		
		// 리뷰내용이 있는 리뷰List select
		List<Review> reviewList = reviewBO.getReviewListBySellerIdHasReviewDesc(sellerId);
		for (Review review : reviewList) {
			// 리뷰 단건의 구매자 정보 가져오기
			UserEntity buyer = userBO.getUserEntityById(review.getBuyerId());
			ReviewCard reviewCard = new ReviewCard();
			reviewCard.setReview(review);
			reviewCard.setBuyer(buyer);
			reviewCardList.add(reviewCard);
		}
		return reviewCardList;
	}
}
