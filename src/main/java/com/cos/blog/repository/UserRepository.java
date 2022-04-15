package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Users;

// jsp의 Dao 역할
// 자동으로 Bean등록이 된다.
// @Repository 생략가능
//<Users, Integer> Users테이블을 관리하고 기본키(userid)가 Integer 타입
// JpaRepository를 상속받으면 데이터를 save, update, delete 할 수 있다.
public interface UserRepository extends JpaRepository<Users, Integer>{
	// SELECT*FROM USERS WHERE USERID=?;
	Optional<Users> findByUserid(String userid);
}
