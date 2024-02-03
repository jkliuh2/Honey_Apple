package com.honeyapple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

	
	// 홈페이지 화면
	// url: http://localhost/honey-apple
	@GetMapping("/honey-apple")
	public String honeyApple(Model model) {
		model.addAttribute("viewName", "mainpage/homepage");
		model.addAttribute("titleName", "꿀사과마켓");
		return "template/layout";
	}
}
