package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping({ "", "/" })
	public String index() { // (@AuthenticationPrincipal PrincipalDetail principal)로 로그인 아이디를 알 수 있다.
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() { // (@AuthenticationPrincipal PrincipalDetail principal)로 로그인 아이디를 알 수 있다.
		return "board/saveForm";
	}
	

}
