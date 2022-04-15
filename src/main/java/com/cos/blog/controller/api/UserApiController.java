package com.cos.blog.controller.api;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
// import com.cos.blog.model.RoleType;
import com.cos.blog.model.Users;
import com.cos.blog.service.UserService;

// 데이터만 리턴하니까 RestController
@RestController
public class UserApiController {
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody Users user){
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody Users user){
		userService.회원수정(user);

		if(user.getOauth()==null||user.getOauth().equals("")) {
			// 소셜 계정 로그인 유저가 아닐때
			// 트랜잭션 종료 후 DB내용 변경 뒤 세션 수동 등록
			Authentication autentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserid(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(autentication);
		}else{
			// 카카오 등 소셜 계정 유저일 때
			Authentication autentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserid(),cosKey));
			SecurityContextHolder.getContext().setAuthentication(autentication);
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/user/{id}")
	public ResponseDto<Integer> delete(@PathVariable int id, HttpSession session){
		userService.회원탈퇴(id);
		session.invalidate(); // 회원데이터 삭제 후 로그아웃 시킴
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
}
