package com.honeyapple.chatRoomView.domain;

import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chatMessage.entity.ChatMessageEntity;
import com.honeyapple.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChatRoomView {

	ChatEntity chat; // 채팅방 정보 - 1:1 매칭
	
	UserEntity seller; // 판매자 유저정보 
	
	UserEntity buyer; // 구매자 유저정보
	
	ChatMessageEntity latestChatMessage; // 가장 마지막 메시지에 대한 정보
}
