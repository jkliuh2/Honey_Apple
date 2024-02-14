package com.honeyapple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.honeyapple.article.bo.ArticleBO;
import com.honeyapple.article.domain.Article;

@Controller
public class MainPageController {
	
	@Autowired
	private ArticleBO articleBO;

	
	/**
	 * 메인페이지 view
	 * http://localhost/honey-apple
	 * @param model
	 * @return
	 */
	@GetMapping("/honey-apple")
	public String honeyApple(Model model) {
		
		// List<Article> 가져오기
		List<Article> articleList = articleBO.getArticleList();
		
		model.addAttribute("viewName", "mainpage/homepage");
		model.addAttribute("titleName", "꿀사과마켓");
		model.addAttribute("nav", "검색");
		model.addAttribute("articleList", articleList);
		return "template/layout";
	}
}
