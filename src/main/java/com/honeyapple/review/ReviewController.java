package com.honeyapple.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.chat.bo.ChatRoomViewBO;
import com.honeyapple.chat.domain.ChatRoomView;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@Autowired
	private ChatRoomViewBO chatRoomViewBO;

	
	// 거래완료 view 페이지로 이동
	@PostMapping("/complete-trade-view")
	public String completeTradeView(
			@RequestParam("chatId") int chatId,
			HttpSession session,
			Model model) {
		
		// 본인확인(구매자)
		int userId = (int)session.getAttribute("userId");
		
		// chatId, userId(buyerId)로 post, chat 가져오기(ChatRoomView)
		ChatRoomView chatRoomView = chatRoomViewBO.getChatRoomViewByChatIdBuyerId(chatId, userId);
		if (chatRoomView == null) {
			return "redirect:/chat/chat-room-view?chatId=" + chatId;
		}
		
		model.addAttribute("viewName", "review/completeTrade");
		model.addAttribute("titleName", "거래 리뷰남기기");
		model.addAttribute("chatRoomView", chatRoomView);
		
		return "template/layout";
	}
}
