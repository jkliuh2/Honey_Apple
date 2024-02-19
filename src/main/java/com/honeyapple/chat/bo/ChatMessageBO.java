package com.honeyapple.chat.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.entity.ChatMessageEntity;
import com.honeyapple.chat.repository.ChatMessageRepository;

@Service
public class ChatMessageBO {
// `chat_message` 테이블에 대한 BO
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	
	// select) 채팅메시지List(오름차순)
	// input:chatId / output:List<ChatMessageEntity>
	public List<ChatMessageEntity> getListChatMessageByChatIdAsc(int chatId) {
		return chatMessageRepository.findByChatIdOrderById(chatId);
	}
	
	
	// select) chatId로 가장 최근 메시지
	public ChatMessageEntity getLatestChatMessageByChatId(int chatId) {
		return chatMessageRepository.findTop1ByChatIdOrderByIdDesc(chatId);
	}
	
	
	// insert) 구매자의 채팅메시지 입력
	public void enterBuyerChatMessage(int chatId, int buyerId, String content) {
		chatMessageRepository.save(ChatMessageEntity.builder()
				.chatId(chatId)
				.buyerId(buyerId)
				.content(content)
				.build());
	}
	// insert) 판매자의 채팅메시지 입력
	public void enterSellerChatMessage(int chatId, int sellerId, String content) {
		chatMessageRepository.save(ChatMessageEntity.builder()
				.chatId(chatId)
				.sellerId(sellerId)
				.content(content)
				.build());
	}
	// insert) 거래상태 메시지 입력(예약, 예약취소, 거래완료 등)
	// input: chatId, content(예약완료, 예약취소, 거래완료) / output:X
	public void enterStatusMessage(int chatId, String content) {
		chatMessageRepository.save(ChatMessageEntity.builder()
				.chatId(chatId)
				.content(content)
				.build());
	}
}
