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
		// 일단, postId/chatId가 모두 없는 경우는 컨트롤러에서 막음.
		
		ChatEntity chat = null; // 채팅메시지가 쓰여진 채팅방(null로 초기화)
		
		// A. 채팅방 select
		if (chatId == null) { // A-1. chatId가 null로 들어옴.
			// 무조건 userId == buyerId, postId는 무조건 존재함.
			// postId로 chat 가져오기.
			chat = chatBO.getChatEntityByPostIdBuyerId(postId, userId);
			
			if (chat == null) { // A-1-1. ★ 채팅방이 진짜 없는 경우.
				// 1. 채팅방 생성
				chat = chatBO.addChat(postId, userId);
				
				// 2. 채팅메시지 insert
				chatMessageBO.enterBuyerChatMessage(chat.getId(), userId, content);
				
				// 3. chatId 리턴 - ★ 메소드 끝
				return chat.getId();
			}
		} else {
			// A-2. chatId가 존재 -> chatId로 chat 가져오기(무조건 채팅방은 존재하는 상태)
			chat = chatBO.getChatEntityByChatId(chatId);
			// userId는 구매자/판매자 모름.
		}
		
		///// 여기까지 왔다면, chat에는 채팅방정보가 저장되어있다.
		
		// B. 유저의 판매자/구매자 로 나누어서 채팅메시지 insert
		if (chat.getBuyerId() == userId) {
			// 로그인유저 == 구매자
			chatMessageBO.enterBuyerChatMessage(chat.getId(), userId, content);
		} else {
			// 로그인유저 == 판매자
			chatMessageBO.enterSellerChatMessage(chat.getId(), userId, content);
		}
		
		// C. chatId 리턴
		return chat.getId();
	}
}
