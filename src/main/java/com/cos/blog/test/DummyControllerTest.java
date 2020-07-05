package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아닌 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	// http://localhost:8080/blog/dummy/join (요청)
	// http의 body에 username,password,email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value(약속된 규칙)
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
	//{id} 주소로 param 정달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾으면 현 데이터에는 없으므로 return이 null
		// Optional로 User 객체를 감싸서 가져오면 null인지 판단하여 return
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() { // AOP 사용 후 에러페이지 지정해주자.
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : "+id);
			}
		});
		/* 람다식 표현 - 더 간단하다.(하지만 어렵다..)
		 * User user = userRepository.findById(id).orElseThrow(()-> { return new
		 * IllegalArgumentException("해당 유저는 존재하지 않습니다. id : "+id); });
		 */
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환(웹브라우저가 이해할 수 있는 데이터) -> json
		// 스프링부트 = MessageConverter - 응답시에 자동 작동
		// 자바 오브젝트를 리턴하면 Jackson 라이브러리 호출하여 오브젝트를 json으로 변환 해준다.
		return user;
	}

	// 전체 리스트 이므로 param을 받을 필요가 없음
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한 페이지 당 2건의 데이터를 리턴받아 보기 
	@GetMapping("dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC)Pageable pageable){
		//Page<User>을 리턴하게 되면 각 페이지의 세부사항도 받을 수 있다.
		Page<User> pagingUser = userRepository.findAll(pageable);
		//세부사항 없이 리스트만 보여줄 것이므로 getContent() 사용 후 리스트화 해준다.
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	// userRepository.save(user);
	// save함수는 id를 전달하지 않으면 insert를 하고,
	//            id를 전달하면, 또 해당 id에 대한 데이터가 있으면 update를,
	//									 id에 대한 데이터가 없으면 insert를한다.
	// email, password를 수정 (json을 이용해보자)
	@Transactional // save를 하지 않아도 업데이트가 된다!
	@PutMapping("dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id : "+id);
		System.out.println("password : "+ requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		
		return null;
		
	}

}



















