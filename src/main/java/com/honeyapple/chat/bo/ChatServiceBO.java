package com.honeyapple.chat.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.entity.ChatEntity;

@Service
public class ChatServiceBO {
// /chat 관련 API에 대한 BO

	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageBO chatMessageBO;
	
	
	// 채팅메시지 입력 API
	// input:chatId(null가능), postId, userId(로그인), content / output:int(chatId)
	public int enterChatMessage(Integer chatId, int postId, int userId, String content) {
		// A. 채팅방이 없을 경우
		if (chatId == null) {
			// userId는 무조건 buyer
			// 1. 채팅방 생성
			ChatEntity chat = chatBO.addChat(postId, userId);
			
			// 2. 채팅메시지 insert
			int newChatId = chat.getId();
			chatMessageBO.enterBuyerChatMessage(newChatId, userId, content);
			
			// 3. chatId 리턴
			return newChatId;
		}
		
		// B. 채팅방이 존재할 경우
		// 1. 채팅방 select
		ChatEntity chat = chatBO.getChatEntityByChatId(chatId);
		
		// 2. 유저의 판매자/구매자 로 나누어서 채팅메시지 insert
		if (chat.getBuyerId() == userId) {
			// 로그인유저 == 구매자
			chatMessageBO.enterBuyerChatMessage(chatId, userId, content);
		} else {
			// 로그인유저 == 판매자
			chatMessageBO.enterSellerChatMessage(chatId, userId, content);
		}
		
		// 3. chatId 리턴
		return chatId;
	}
}
