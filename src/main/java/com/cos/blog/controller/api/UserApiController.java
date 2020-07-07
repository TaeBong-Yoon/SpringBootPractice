package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	/*
	 * @Autowired private HttpSession session;
	 */

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("API Controller : save call");
		// httpstatus.ok.value() = 200 = 데이터 전송이 완료됐다는 뜻
		user.setRole(RoleType.USER);
		userService.joinService(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	

}

//기본적인 로그인 방식
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session) { // 위 autowired 또는 이 방법 사용하여 session 쓰자
	 * System.out.println("API Controller : login call"); User principal =
	 * userService.loginService(user); // principal(접근주체)
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); }
	 * 
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
	 */
