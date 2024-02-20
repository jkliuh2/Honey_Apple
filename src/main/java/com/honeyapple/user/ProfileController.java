package com.honeyapple.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.article.bo.ArticleBO;
import com.honeyapple.article.domain.Article;
import com.honeyapple.review.bo.ReviewServiceBO;
import com.honeyapple.review.domain.ReviewCard;
import com.honeyapple.user.bo.UserBO;
import com.honeyapple.user.entity.UserEntity;

@Controller
public class ProfileController {
// 프로필 관련 Controller
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ArticleBO articleBO;
	
	@Autowired
	private ReviewServiceBO reviewServiceBO;
	
	
	/**
	 * profile view
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@GetMapping("/profile")
	public String profile(
			@RequestParam("userId") int userId,
			Model model) {
		
		// 프로필 유저의 정보 가져오기
		UserEntity user = userBO.getUserEntityById(userId);
		
		// 프로필 유저의 판매 물품 리스트 가져오기(status != "판매완료")
		List<Article> articleList = articleBO.getArticleListBySellerIdStatus(userId, null, "판매완료");
		
		// 응답
		model.addAttribute("user", user); // 프로필 유저 정보
		model.addAttribute("articleList", articleList); // 판매물품 리스트 정보
		model.addAttribute("viewName", "profile/profile");
		model.addAttribute("titleName", user.getNickname());
		return "template/layout";
	}
	
	
	// profile 화면 메뉴변경 API
	@GetMapping("/profile/change-menu")
	public String changeMenu(
			@RequestParam("menu") String menu,
			@RequestParam("userId") int userId, // 프로필유저의 id
			Model model) {
		
		if (menu.equals("reviewList")) {
			// reviewList 가져와서 담기
			List<ReviewCard> reviewCardList = reviewServiceBO.getReviewCardListBySellerId(userId);
			model.addAttribute("reviewCardList", reviewCardList);
		} else if (menu.equals("soldList")) {
			// 판매완료List 가져와서 담기
			List<Article> articleList = articleBO.getArticleListBySellerIdStatus(userId, "판매완료", null);
			model.addAttribute("articleList", articleList);
		}
		return "profile/" + menu;
	}
}
