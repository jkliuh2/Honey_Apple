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
	
	// insert
	public ChatEntity addChat(int postId, int buyerId) {
		
	}
}
