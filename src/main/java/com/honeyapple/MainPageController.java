package com.honeyapple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

	
	/**
	 * 메인페이지 view
	 * http://localhost/honey-apple
	 * @param model
	 * @return
	 */
	@GetMapping("/honey-apple")
	public String honeyApple(Model model) {
		model.addAttribute("viewName", "mainpage/homepage");
		model.addAttribute("titleName", "꿀사과마켓");
		model.addAttribute("nav", "검색");
		return "template/layout";
	}
}
