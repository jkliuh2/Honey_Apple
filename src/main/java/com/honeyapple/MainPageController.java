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
	
	
	// 더보기+검색 페이지
	@GetMapping("/honey-apple/search-view")
	public String searchView(Model model) {
		
		// List<Article> 가져오기(임시)
		List<Article> articleList = articleBO.getArticleList();
		
		// 응답값
		model.addAttribute("viewName", "mainpage/searchView");
		model.addAttribute("titleName", "검색");
		model.addAttribute("nav", "검색");
		model.addAttribute("articleList", articleList);
		return "template/layout";
	}
	
	// 테스트용 페이지
	@GetMapping("/test")
	public String test(Model model) {
		model.addAttribute("viewName", "test/test");
		model.addAttribute("titleName", "테스트");
		return "template/layout";
	}
	// 테스트용 페이지
	@GetMapping("/test2")
	public String test2(Model model) {
		model.addAttribute("viewName", "test/mapShowTest");
		model.addAttribute("titleName", "테스트2");
		return "template/layout";
	}
}
