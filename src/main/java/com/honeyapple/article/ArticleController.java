package com.honeyapple.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.article.bo.ArticleBO;
import com.honeyapple.article.domain.Article;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/article")
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleBO articleBO;
	

	// 글 상세 페이지 view
	// http://localhost/article/detail-view?postId=
	@GetMapping("/detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		// 세션에서 로그인된 유저 정보 가져오기 - (비-로그인도 볼 수 있음)
		Integer userId = (Integer)session.getAttribute("userId");
		
		// DB select (Article)
		Article article = articleBO.getArticleByPostIdUserId(postId, userId);
		
		// 응답
		model.addAttribute("article", article);
		model.addAttribute("viewName", "article/detail");
		model.addAttribute("titleName", "꿀템글");
		
		return "template/layout";
	}	
}
