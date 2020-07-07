package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록해준다. IoC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional // 회원가입을 하나의 트랙잭션으로 만들어준다.
	public void joinService(User user) {//회원가입
		userRepository.save(user);
	}
	
	
}

/* 기본적인 로그인 방법
 * @Transactional(readOnly = true) // Select를 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성
 * 유지 가능) public User loginService(User user) { return
 * userRepository.findByUsernameAndPassword(user.getUsername(),
 * user.getPassword()); }
 */
