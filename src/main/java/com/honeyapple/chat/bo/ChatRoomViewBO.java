package com.honeyapple.chat.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.domain.ChatRoomView;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.entity.ChatMessageEntity;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@Service
public class ChatRoomViewBO {
// CharRoomView domain에 대한 BO
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	
	// postId로 List<ChatRoomView> 가져오기 (판매자 기준이라서 판매자의 정보는 필요없다.)
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
			ChatMessageEntity LatestMessage = chatMessageBO.getLatestChatMessageByChatId(chat.getId());
			chatRoomView.setLatestChatMessage(LatestMessage); // 가장 최근 채팅메시지
			
			// 구매자 유저정보
			UserEntity buyer = userBO.getUserEntityById(chat.getBuyerId());
			chatRoomView.setBuyer(buyer);
			
			// List에 넣기
			chatRoomViewList.add(chatRoomView);
		}
		
		return chatRoomViewList;
	}
}
