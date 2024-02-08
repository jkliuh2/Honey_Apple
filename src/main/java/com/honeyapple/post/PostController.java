package com.honeyapple.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {


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
	
}
