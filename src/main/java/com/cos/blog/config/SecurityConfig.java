package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

// 아래 세개의 annotation이 스프링 시큐리티 사용시 반드시 필요한 것들!
@Configuration // 빈등록(IoC관리)
@EnableWebSecurity // 스프링 시큐리티가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC가 된다.
	public BCryptPasswordEncoder encodePWD() { // 고정길이의 문자열로 바꿔준다. (비밀번호 해시)
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인을 할 때,
	// 가로챈 패스워드가 뭘로 해쉬가 되어 가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋다)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // auth 경로는
				.permitAll() // 누구나 들어올 수 있다.
				.anyRequest() // 그걸 제외한 모든 요청은 
				.authenticated() // 인증이 되야한다.
			.and()
				.formLogin() // localhost://8000 로 접근하면 자동으로
				.loginPage("/auth/loginForm") // /auth/loginForm을 띄어준다
				 // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채고, 대신 로그인을 진행한다.
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); // 성공했을때의 기본 url을 "/"로 지정한다.
		
	}
	
}
