package com.honeyapple.chatMessage.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chatMessage.entity.ChatMessageEntity;
import com.honeyapple.chatMessage.repository.ChatMessageRepository;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;

@Service
public class ChatMessageBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	
	// 채팅메시지List select
	// input:chatId / output:List<ChatMessageEntity>
	public List<ChatMessageEntity> getListChatMessageByChatIdDesc(int chatId) {
		return chatMessageRepository.findByChatIdOrderByIdDesc(chatId);
	}
	
	// 채팅메시지 insert
	public void addChatMessage(Integer chatId, int postId, int userId, String content) {
		if (chatId == null) {
			// post 정보 select
			Post post = postBO.getPostById(postId); 
			
			// 채팅메시지 처음으로 입력.
			chatBO.addChat(int postId, int userId);
		}
	}
}
