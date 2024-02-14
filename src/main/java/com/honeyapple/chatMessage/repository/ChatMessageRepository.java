package com.honeyapple.chatMessage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeyapple.chatMessage.entity.ChatMessageEntity;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Integer> {

	public List<ChatMessageEntity> findByChatIdOrderById(int chatId);
	
	public ChatMessageEntity findTop1ByChatIdOrderByIdDesc(int chatId);
}
