package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
//자동으로 bean 등록이 된다.
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

	// SELECT * FROM user WHERE username = ?
	Optional<User> findByUsername(String username);
	
}

//기본적인 CRUD를 할 수 있다.
	// JPA Naming 전략,쿼리
	// SELECT * FROM user WHERE username = ? AND password = ?; 쿼리가 동작한다!
	// User findByUsernameAndPassword(String username,String password);
	
	// 또는 이런 방식으로도 가능하다. (복잡한 쿼리에서 사용하면 좋을듯 함)
	// @Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
	// User login(String username,String password);