package com.honeyapple.review.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chat.bo.ChatMessageBO;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;

@Service
public class ReviewServiceBO {
	
	@Autowired
	private PostBO postBO;
	
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
}
