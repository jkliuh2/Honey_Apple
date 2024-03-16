package com.honeyapple.chat.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.domain.ChatRoomView;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.entity.ChatMessageEntity;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@Service
public class ChatRoomViewBO {
// CharRoomView domain에 대한 BO
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	
	// 구매자가 chatId, buyerId로 ChatRoomView select
	// input:chatId, buyerId / output:ChatRoomView(post, chat)
	public ChatRoomView getChatRoomViewByChatIdBuyerId(int chatId, int buyerId) {
		ChatRoomView chatRoomView = new ChatRoomView();
		ChatEntity chat = chatBO.getChatEntityByChatIdBuyerId(chatId, buyerId);
		if (chat == null || !chat.getTradeStatus().equals("예약")) {
			return null;
		}
		Post post = postBO.getPostById(chat.getPostId());
		chatRoomView.setPost(post);
		chatRoomView.setChat(chat);
		return chatRoomView;
	}
	
	
	// 판매자가 postId로 List<ChatRoomView> 가져오기 (판매자의 정보는 필요없다.)
	// input: postId, sellerId(판매자id) / output:List<ChatRoomView>
	public List<ChatRoomView> getChatRoomViewListByPostId(int postId) {
		// ChatRoomView에 들어가야할 정보들
		// 1. 구매자유저정보, 2. 채팅방정보, 3. 채팅메시지(가장 최근것 1개)
		
		List<ChatRoomView> chatRoomViewList = new ArrayList<>();
		
		// 채팅방 정보 가져오기
		List<ChatEntity> chatList = chatBO.getChatEntityListByPostId(postId);
		for (ChatEntity chat : chatList) {
			ChatRoomView chatRoomView = new ChatRoomView();
			
			// 채팅방 정보
			chatRoomView.setChat(chat);
			
			// 가장 최근 채팅메시지
			List<ChatMessageEntity> chatMessageList = chatMessageBO.getListChatMessageByChatIdAsc(chat.getId());
			for (int i = 0; i < chatMessageList.size(); i++) { // 최근 메시지부터 순회
				if (chatMessageList.get(i).getBuyerId() == null 
						&& chatMessageList.get(i).getSellerId() == null) {
					// 메시지가 상태메시지임.
					continue;
				}
				// 위에서 걸리지 않으면 -> 판매자or구매자의 메시지
				chatRoomView.setLatestChatMessage(chatMessageList.get(i)); // 가장 최근 채팅메시지
			}
			
			// 구매자 유저정보
			UserEntity buyer = userBO.getUserEntityById(chat.getBuyerId());
			chatRoomView.setBuyer(buyer);
			
			// List에 넣기
			chatRoomViewList.add(chatRoomView);
		}
		
		return chatRoomViewList;
	}
	
	
	
	// 채팅방 view 정보 가져오기 API (구매자+판매자)
	public ChatRoomView getChatRoomViewByFields(Integer postId, Integer chatId, int userId) {
		ChatRoomView chatRoomView = new ChatRoomView();
		
		ChatEntity chat = null;
		// A. chatId == null -> postId는 무조건 존재, userId는 무조건 구매자(판매자는 이렇게 접근 불가능)
		if (chatId == null) {
			// A-1. 채팅방이 진짜 없는지 확인하기
			chat = chatBO.getChatEntityByPostIdBuyerId(postId, userId);
			
			if (chat == null) {
				// A-1-1. 진짜로 채팅방이 없음.
				// 1. 채팅방이 존재하지 않는 경우.
				Post post = postBO.getPostById(postId);
				UserEntity seller = userBO.getUserEntityById(post.getSellerId());
				
				if (userId == seller.getId()) {
					// 채팅방이 존재하지 않을 때, 판매자는 채팅방에 접근 불가능(존재하지 않으므로)
					return chatRoomView; // 데이터 없는 ChatRoomView 리턴
				}
				
				chatRoomView.setPost(post);
				chatRoomView.setSeller(seller);
				return chatRoomView;
			}
			// A-1-2. 채팅방이 존재한다? 그냥 아래로 진행.
			
		} else {
			// A-2. chatId값이 존재함.
			chat = chatBO.getChatEntityByChatId(chatId);
		}
		
		// 여기까지 왔다면, chat에 채팅방 정보가 들어가있음.
		
		// B. 채팅방이 존재하기 때문에, 채팅방과 관련된 정보들을 DB에서 select한다.
		Post post = postBO.getPostById(chat.getPostId());
		UserEntity seller = userBO.getUserEntityById(post.getSellerId());
		UserEntity buyer = userBO.getUserEntityById(chat.getBuyerId());
		
		if (userId != seller.getId() && userId != buyer.getId()) {
			// 로그인 유저가 채팅방과 전혀 상관없는 사람일 경우.
			return chatRoomView;
		}
		List<ChatMessageEntity> chatMessageList = chatMessageBO.getListChatMessageByChatIdAsc(chat.getId());
		
		// C. select한 정보들을 ChatRoomView 객체에 담아서 return
		chatRoomView.setPost(post);
		chatRoomView.setSeller(seller);
		chatRoomView.setBuyer(buyer);
		chatRoomView.setChat(chat);
		chatRoomView.setChatMessageList(chatMessageList);
		return chatRoomView;
		
		// 리턴 종류
		// 1. 비어있는 ChatRoomView -> 컨트롤러에서 내부의 상태 확인하고 postId로 post로 리다이렉트
		// 2. 뭔가 차있는 ChatRoomView -> 모델에 집어넣고 채팅방 jsp 리턴 (최소한 post, seller는 차있다)
		// 3. 아예 잘못된 경우는 컨트롤러에서 처리
	}
}
