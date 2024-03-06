package com.honeyapple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.article.bo.ArticleBO;
import com.honeyapple.article.domain.Article;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainPageController {
	
	@Autowired
	private ArticleBO articleBO;
	
	@Autowired
	private UserBO userBO;

	
	/**
	 * 메인페이지 view
	 * http://localhost/honey-apple
	 * @param model
	 * @return
	 */
	@GetMapping("/honey-apple")
	public String honeyApple(Model model, HttpSession session) {
		
		// 로그인 -> hometown 정보 없으면 동네설정 페이지로 리다이렉트 하기
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId != null) {
			UserEntity user = userBO.getUserEntityById(userId);
			if (user.getHometown() == null) {
				return "redirect:/user/set-hometown-view";
			}
		}
		
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
		model.addAttribute("articleList", articleList);
		return "template/layout";
	}
	
	
	// 검색 페이지 검색 API -> 리턴 jsp
	@PostMapping("/search")
	public String search(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "sido", required = false) Integer sido,
			@RequestParam(name = "sigugun", required = false) Integer sigugun,
			@RequestParam(name = "dong", required = false) Integer dong,
			@RequestParam("juso") boolean juso,
			Model model) {
		
		// DB - 검색결과 List<Article>
		List<Article> articleList = articleBO.searchArticle(keyword, sido, sigugun, dong, juso);
		
		// Model에 List담고 리턴
		model.addAttribute("articleList", articleList);
		return "article/articleList";
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
