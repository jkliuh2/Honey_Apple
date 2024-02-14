package com.honeyapple.chatMessage.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chatMessage.entity.ChatMessageEntity;
import com.honeyapple.chatMessage.repository.ChatMessageRepository;

@Service
public class ChatMessageBO {
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	
	// 채팅메시지List select
	// input:chatId / output:List<ChatMessageEntity>
	public List<ChatMessageEntity> getListChatMessageByChatIdAsc(int chatId) {
		return chatMessageRepository.findByChatIdOrderById(chatId);
	}
	
	// 채팅메시지 insert -> return chatId
	public int addChatMessage(Integer chatId, int postId, int userId, String content) {
		if (chatId == null) {
			// 채팅방이 존재하지 않을 경우 -> 무조건 구매자가 채팅친 것.
			// 1. 채팅방 생성
			ChatEntity chat = chatBO.addChat(postId, userId);
			
			// 2. 채팅메시지 생성
			// ChatMessage builder
			ChatMessageEntity chatMessage = ChatMessageEntity.builder()
					.chatId(chat.getId())
					.buyerId(userId)
					.content(content)
					.build();
			chatMessageRepository.save(chatMessage);
			
			// 3. chatId 리턴
			return chat.getId();
			
		} else {
			// 채팅방이 존재함.
			// 일단 chat(채팅방) 가져온다.
			ChatEntity chat = chatBO.getChatEntityByChatId(chatId);
			if (userId == chat.getBuyerId()) {
				// 로그인된 유저 == 구매자
				chatMessageRepository.save(ChatMessageEntity.builder()
						.chatId(chat.getId())
						.buyerId(userId)
						.content(content)
						.build());
			} else {
				// 로그인 유저 == 판매자
				chatMessageRepository.save(ChatMessageEntity.builder()
						.chatId(chat.getId())
						.sellerId(userId)
						.content(content)
						.build());
			}
			
			// ChatId 리턴
			return chatId;
		}
	}
	
	
	// select) chatId로 가장 최근 메시지
	public ChatMessageEntity getLatestChatMessageByChatId(int chatId) {
		return chatMessageRepository.findTop1ByChatIdOrderByIdDesc(chatId);
	}
}
