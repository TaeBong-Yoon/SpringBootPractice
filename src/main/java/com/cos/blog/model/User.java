package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder//builder패턴
@AllArgsConstructor
@NoArgsConstructor
@Data
//ORM -> JAVA Object -> 테이블로 매핑해준다
//User 클래스가 MySQL에 테이블 생성이 된다. 
@Entity
//@DynamicInsert // insert 시에 null인 값은 제외하고 insert 진행, 
public class User {
	
	@Id //Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 DB의 넘버링 전략을 따라감.
	private int id; // auto_increment , userIndex
	
	@Column(nullable = false,length = 30) // not null,20
	private String username; // ID
	
	@Column(nullable = false,length = 100) // 해쉬(비밀번호 암호화) 하기 위해 넉넉하게 길이 조정.
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
//	@ColumnDefault("'user'")
//	DB는 RoleType이 없음
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. ADMIN,USER
	
	@CreationTimestamp // 시간이 자동으로 입력된다.
	private Timestamp createDate;

}
