package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	/*
	 * @GetMapping({ "", "/" }) public String index() { // (@AuthenticationPrincipal
	 * PrincipalDetail principal)로 로그인 아이디를 알 수 있다. return "index"; }
	 */

	@GetMapping({ "", "/" })
	public String index(Model model) {
		model.addAttribute("boards", boardService.boardList());
		return "index";
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

}
