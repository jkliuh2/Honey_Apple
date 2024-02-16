package com.honeyapple.chat.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.repository.ChatRepository;

@Service
public class ChatBO {
// `chat` 테이블에 대한 BO
	
	@Autowired
	private ChatRepository chatRepository;

	
	// select
	// input:postId, buyerId / output:ChatEntity
	public ChatEntity getChatEntityByPostIdBuyerId(int postId, int buyerId) {
		return chatRepository.findByPostIdAndBuyerId(postId, buyerId);
	}
	
	// select(id)
	public ChatEntity getChatEntityByChatId(int chatId) {
		return chatRepository.findById(chatId).orElse(null);
	}
	
	// select(postId) - List
	public List<ChatEntity> getChatEntityListByPostId(int postId) {
		return chatRepository.findByPostId(postId);
	}
	
	// insert
	public ChatEntity addChat(int postId, int buyerId) {
		ChatEntity chat = ChatEntity.builder()
				.postId(postId)
				.buyerId(buyerId)
				.tradeStatus("제안중")
				.build();
		return chatRepository.save(chat); // insert 성공하면 id값 들어간다.
	}
}
