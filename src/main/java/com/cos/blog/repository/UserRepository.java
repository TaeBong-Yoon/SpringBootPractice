package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//자동으로 bean 등록이 된다.
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

	// 기본적인 CRUD를 할 수 있다.
	
}
