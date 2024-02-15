package com.honeyapple.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.honeyapple.chat.bo.ChatBO;
import com.honeyapple.chat.bo.ChatMessageBO;
import com.honeyapple.chat.bo.ChatRoomViewBO;
import com.honeyapple.chat.domain.ChatRoomView;
import com.honeyapple.chat.entity.ChatEntity;
import com.honeyapple.chat.entity.ChatMessageEntity;
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
	
	@Autowired
	private ChatRoomViewBO chatRoomViewBO;

	
	/**
	 * 채팅방 view로 이동
	 * 
	 * @param postId // 필수 파라미터
	 * @param chatId // null가능(로그인 유저가 구매자일 경우, null로 들어올 수 있음)
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/chat-room-view")
	public String chatRoomView(
			@RequestParam("postId") int postId,
			@RequestParam(name = "chatId", required = false) Integer chatId,
			HttpSession session,
			Model model
			) {
		
		
		// db select + model 
		// 게시글 정보
		Post post = postBO.getPostById(postId);
		
		// 판매자 정보
		UserEntity seller = userBO.getUserEntityById(post.getSellerId());
		
		// chat_room 존재여부
		if (chatId == null) {
			// chatId == null 
			// 일단 로그인 유저는 구매자가 확실함.
			int buyerId = (int)session.getAttribute("userId");
			
			// -> 채팅방 존재하는지 확인
			ChatEntity chatRoom = chatBO.getChatEntity(postId, buyerId);
			if (chatRoom != null) {
				// 채팅방 존재하면
				// 1. model에 넣기
				model.addAttribute("chatRoom", chatRoom);
				
				// 2. 채팅메시지 가져오기
				List<ChatMessageEntity> chatMessageList = chatMessageBO.getListChatMessageByChatIdAsc(chatRoom.getId());
				model.addAttribute("chatMessageList", chatMessageList);
			} 
			// 채팅방 없으면 -> 그냥 리턴
			
		} else { // 채팅방 존재.
			// 1. 로그인 유저가 채팅방의 구매자or판매자 확인
			int userId = (int)session.getAttribute("userId");
			ChatEntity chatRoom = chatBO.getChatEntityByChatId(chatId);
			
			if (chatRoom.getBuyerId() != userId && post.getSellerId() != userId) {
				// 유저 정보 일치X -> 해당 게시글로 리다이렉트
				return "redirect:/article/detail-view?postId=" + postId;
			}
			
			// 2. 채팅방, 채팅리스트 가져오기
			List<ChatMessageEntity> chatMessageList = chatMessageBO.getListChatMessageByChatIdAsc(chatId);
			model.addAttribute("chatRoom", chatRoom);
			model.addAttribute("chatMessageList", chatMessageList);
		}
		
		
		// 남은 응답값
		model.addAttribute("viewName", "chat/chatRoom");
		model.addAttribute("titleName", "거래채팅방");
		model.addAttribute("post", post);
		model.addAttribute("seller", seller);
		
		return "template/layout";
	}
	
	
	/**
	 * 채팅메시지 입력 API
	 * 로그인된 유저가 판매자or구매자
	 * 입력이 끝나면 채팅방view로 리다이렉트(postId, chatId를 param으로 전달)
	 * 
	 * @param chatId
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/enter-message")
	public String enterMessage(
			@RequestParam(name = "chatId", required = false) Integer chatId,
			@RequestParam("postId") int postId,
			@RequestParam(name = "content", required = true) String content,
			HttpSession session,
			RedirectAttributes redirectAttributes
			) {
		
		// 로그인 정보 가져오기
		int userId = (int)session.getAttribute("userId");
		
		// db insert
		int afterChatId = chatMessageBO.addChatMessage(chatId, postId, userId, content);
		
		// 리다이렉트
		redirectAttributes.addAttribute("chatId", afterChatId);
		redirectAttributes.addAttribute("postId", postId);
		return "redirect:/chat/chat-room-view";
	}
	
	
	// /chat/chat-list-view?postId=
	// 판매자전용) param으로 들어온 postId로 등록된 채팅방 list를 표시하는 view로 이동
	@GetMapping("/chat-list-view")
	public String chatListView(
			@RequestParam("postId") int postId,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		// 1. 로그인유저 == 판매자 인지 확인.
		int userId = (int)session.getAttribute("userId");
		Post post = postBO.getPostById(postId);
		if (post.getSellerId() != userId) {
			// 판매자 본인이 아닐 경우. -> article로 리다이렉트
			redirectAttributes.addAttribute("postId", postId); // postId값 param으로 전달
			return "redirect:/article/detail-view";
		}
		
		// 2. postId와 일치하는 채팅방List 가져오기(List<ChatRoomView>)
		List<ChatRoomView> chatRoomViewList = chatRoomViewBO.getChatRoomViewListByPostId(postId);
		
		// 3. 응답값
		model.addAttribute("viewName", "chat/chatList");
		model.addAttribute("titleName", "거래 채팅방 목록");
		model.addAttribute("chatRoomViewList", chatRoomViewList);
		return "template/layout";
	}
}
