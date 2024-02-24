package com.honeyapple.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.article.bo.ArticleBO;
import com.honeyapple.article.domain.Article;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyTradeController {
	
	@Autowired
	private ArticleBO articleBO;

	
	// 구매자) my-trade 화면 이동(view)
	// userId가 관심등록한 articleList를 Model에 담아야한다.
	// 필터에서 로그인 권한검사O
	@GetMapping("/my-trade")
	public String myTrade(
			HttpSession session,
			Model model) {
		
		// 세션에서 userId 가져오기(이게 buyerId)
		int userId = (int)session.getAttribute("userId");
		
		// DB - user가 관심을 표시한 글 List<Article> 가져오기
		List<Article> articleList = articleBO.getMyTradeArticleListByBuyerId(userId, null);
		
		// 응답값
		model.addAttribute("articleList", articleList);
		model.addAttribute("viewName", "myTrade/myTrade");
		model.addAttribute("titleName", "내 꿀템들");
		return "template/layout";
	}
	
	
	// my-trade 화면에서의 메뉴변경 API
	@GetMapping("/my-trade/change-menu")
	public String changeMenu(
			@RequestParam("menu") String menu,
			HttpSession session,
			Model model) {
		
		// 유저정보 꺼내기(buyerId)
		int userId = (int)session.getAttribute("userId");
		
		// DB - menu에 따른 데이터 가져오기
		List<Article> articleList = articleBO.getMyTradeArticleListByBuyerId(userId, menu);
		model.addAttribute("articleList", articleList);
		
		return "myTrade/articleList";
	}
}
