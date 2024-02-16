package com.honeyapple.chat.domain;

import java.util.List;

import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.entity.ChatMessageEntity;
import com.honeyapple.post.domain.Post;
import com.honeyapple.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChatRoomView {
	
	Post post; // 게시글 정보

	UserEntity seller; // 판매자 유저정보 
	
	UserEntity buyer; // 구매자 유저정보
	
	ChatEntity chat; // 채팅방 정보 - 1:1 매칭
	
	ChatMessageEntity latestChatMessage; // 가장 마지막 메시지에 대한 정보 - 삭제예정
	
	List<ChatMessageEntity> chatMessageList; // 채팅 리스트
}
