package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록해준다. IoC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional // 회원가입을 하나의 트랙잭션으로 만들어준다.
	public void joinService(User user) {// 회원가입
		String rawPassword = user.getPassword(); // 원래 비밀번호
		String encPassword = encoder.encode(rawPassword); // 해쉬화 된 비밀번호
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void update(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고,
		// 영속화된 User 오브젝트를 수정. select를 해서 User 오브젝트를 DB로 가져오는 이유: 영속화
		// 영속화된 오브젝트를 변경하면 자동으로 update문을 날려준다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Vallidation 체크 : 카카오 로그인은 수정 못하도록 서버에서도 막아준다.
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		// 회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = auto commit
		// 영속화된 persistence 객체의 변화가 감지되면 더티체킹이 되어
		// 자동으로 update문을 날려줌.
	}

	@Transactional(readOnly = true)
	public User findUser(String username) {

		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;

	}
}

/*
 * 기본적인 로그인 방법
 * 
 * @Transactional(readOnly = true) // Select를 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성
 * 유지 가능) public User loginService(User user) { return
 * userRepository.findByUsernameAndPassword(user.getUsername(),
 * user.getPassword()); }
 */
