package com.honeyapple.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeyapple.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

	public ChatEntity findByPostIdAndBuyerId(int postId, int buyerId);
	public ChatEntity findByIdAndBuyerId(int id, int buyerId);
	
	public List<ChatEntity> findByPostId(int postId);
	public List<ChatEntity> findByBuyerIdOrderByUpdatedAtDesc(int buyerId);
	public List<ChatEntity> findByBuyerIdAndTradeStatusOrderByUpdatedAtDesc(
			int buyerId, String tradeStatus);
	
	public int countByPostId(int postId);
}
