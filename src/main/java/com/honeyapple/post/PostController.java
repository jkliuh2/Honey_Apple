package com.honeyapple.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeyapple.post.bo.PostBO;
import com.honeyapple.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	private PostBO postBO;
	
	
	/**
	 * 글 쓰기 view
	 * http://localhost/post/create-view
	 * @param model
	 * @return
	 */
	@GetMapping("/create-view")
	public String createView(Model model) {
		model.addAttribute("viewName", "post/create");
		model.addAttribute("titleName", "게시글 쓰기");
		return "template/layout";
	}
	
	
	// 글 수정 view
	@GetMapping("/update-view")
	public String updateView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		// 세션 유저 정보
		int userId = (int)session.getAttribute("userId");
		
		// Post select
		Post post = postBO.getPostById(postId);
		if (post.getSellerId() != userId) {
			return "redirect:/article/detail-view?postId" + postId;
		}
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/update");
		model.addAttribute("titleName", "게시글 수정");
		return "template/layout";
	}
}
