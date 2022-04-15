package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cos.blog.model.RoleType;
import com.cos.blog.model.Users;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록함, Ioc
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encodeer;
	
	@Transactional
	public void 회원가입(Users user) {
		String rawPassword = user.getPassword();
		String encPassword = encodeer.encode(rawPassword);	// 비번이 해쉬화
		user.setPassword(encPassword);	// 해쉬화 된 비번을 set
		user.setRoles(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(Users user) {
		Users persistence = userRepository.findById(user.getId()).orElseThrow(()->{
			// 람다식
			// orElseThrow(new Supplier<IllegalArgumentException>(){
			// @Override
			// public IllegalArgumentException get() {
			// return new IllegalArgumentException("회원 찾기 실패 등 예외처리메세지");
			return new IllegalArgumentException("회원 찾기 실패" + user.getId());
		});
		
		if(persistence.getOauth()==null||persistence.getOauth().equals("")) {
			// 소셜 계정 유저가 아닐때 비밀번호와 이메일 수정
			String rawPassword = user.getPassword();
			String encPassword = encodeer.encode(rawPassword);
			persistence.setPassword(encPassword);
			persistence.setEmail(user.getEmail());
		}
		persistence.setUsername(user.getUsername());
		persistence.setPhone(user.getPhone());
		persistence.setZipcode(user.getZipcode());
		persistence.setAddress1(user.getAddress1());
		persistence.setAddress2(user.getAddress2());
	}
	
	@Transactional(readOnly=true)
	public Users 회원찾기(String userid) {
		Users user = userRepository.findByUserid(userid).orElseGet(()->{
			return new Users();
		});
		return user;
	}
	
	@Transactional
	public void 회원탈퇴(int id) {
		userRepository.deleteById(id);
	}
		
	
	
}
