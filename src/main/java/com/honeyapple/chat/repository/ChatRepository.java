package com.honeyapple.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeyapple.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

	public ChatEntity findByPostIdAndBuyerId(int postId, int buyerId);
}
