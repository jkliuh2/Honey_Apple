package com.honeyapple.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chatMessage.bo.ChatMessageBO;
import com.honeyapple.chatMessage.entity.ChatMessageEntity;
import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat")
@Controller
public class ChatController {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ChatBO chatBO;
	
	@Autowired
	private ChatMessageBO chatMessageBO;

	
	// /chat/chat-room-view
	// 구매자) 채팅방으로 이동
	// 비-로그인 상태일 때, 로그인화면으로 리다이렉트 - 필터에서 처리
	@GetMapping("/chat-room-view")
	public String chatRoomView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model
			) {
		
		// 로그인 정보 가져오기(구매자 유저 정보) -> 필터에서 권한검사 처리해서 무조건 int
		int buyerId = (int)session.getAttribute("userId");
		
		// db select
		Post post = postBO.getPostById(postId); // 게시글 정보
		UserEntity seller = userBO.getUserEntityById(post.getSellerId()); // 판매자 유저 정보
		ChatEntity chatRoom = chatBO.getChatEntity(postId, buyerId); // 채팅방 가져오기(Null 가능)
		if (chatRoom != null) {
			List<ChatMessageEntity> chatMessageList = chatMessageBO.getListChatMessageByChatIdDesc(chatRoom.getId());
			model.addAttribute("chatMessageList", chatMessageList);
		}
		
		// 응답값
		model.addAttribute("viewName", "chat/chatRoom");
		model.addAttribute("titleName", "거래채팅방");
		model.addAttribute("post", post);
		model.addAttribute("seller", seller);
		model.addAttribute("chatRoom", chatRoom);
		
		return "template/layout";
	}
	
	
	// 채팅 메시지 입력
	@PostMapping("/enter-message")
	public String enterMessage(
			@RequestParam(name = "chatId", required = false) Integer chatId,
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			HttpSession session
			) {
		
		// 로그인 정보 가져오기
		int userId = (int)session.getAttribute("userId");
		
		// db insert
		addChatMessage(chatId, postId, userId, content);
		
		// 리다이렉트
		return "redirect:/chat/chat-room-view";
	}
}
