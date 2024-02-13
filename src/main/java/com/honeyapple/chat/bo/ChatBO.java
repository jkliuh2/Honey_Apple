package com.honeyapple.chat.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.repository.ChatRepository;

@Service
public class ChatBO {
	
	@Autowired
	private ChatRepository chatRepository;

	
	// select
	// input:postId, buyerId / output:ChatEntity
	public ChatEntity getChatEntity(int postId, int buyerId) {
		return chatRepository.findByPostIdAndBuyerId(postId, buyerId);
	}
	
	// select(id)
	public ChatEntity getChatEntityByChatId(int chatId) {
		return chatRepository.findById(chatId).orElse(null);
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
