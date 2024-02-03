package com.honeyapple.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
// view 컨트롤러

	
	// 회원가입 view
	// url: http://localhost/honey-apple/user/sign-up-view
	@GetMapping("/sign-up-view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/signUp");
		model.addAttribute("titleName", "회원가입");
		return "template/layout";
	}
}
