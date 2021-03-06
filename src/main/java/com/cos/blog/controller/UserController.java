package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.Users;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 사용자 인증 필요 없는 경로 /auth/*
@Controller
public class UserController {
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String login(@RequestParam(value = "error", required = false)String error, 
			@RequestParam(value = "exception", required = false)String exception, Model model) { 
		model.addAttribute("error", error); 
		model.addAttribute("exception", exception); 
		return "user/loginForm"; 
	}

	@PostMapping("/auth/idcheck")
	@ResponseBody
	public int idCheck(@RequestParam("userid") String userid) {
		int cnt = 0;
		System.out.println("전달받은 id:"+userid);
		Users originUser = userService.회원찾기(userid);
		if(originUser.getUserid()==null) {
			System.out.println("cnt : "+cnt);
			// 기존 가입자 없으면 중복아이디가 아니라 사용가능함
			return cnt;
		}else {
			cnt = 1;
			System.out.println("cnt : "+cnt);
			return cnt;
		}
	}
	
	
//	@GetMapping("/auth/loginForm")
//	public String loginForm() {
//		return "user/loginForm";
//	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {	// Date를 리턴해주는 컨트롤러 함수
		// POST방식으로 key=value 카카오에 데이터 요청
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "8d470bb8eca940ae1ee8dab66bebedd4");
		params.add("redirect_uri", "http://localhost:8083/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);	// 바디데이터+헤더
		
		// Http 요청하기(POST)
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST, kakaoTokenRequest, String.class
		);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
				
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
		// HttpHeader와 HttpBody를 하나의 오브젝트로 담기
			HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
					new HttpEntity<>(headers2);
				
		// Http 요청하기(POST) - response 응답받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST, kakaoProfileRequest, String.class
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("카카오 아이디(number): " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("카카오 유저아이디 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("블로그서버 패스워드 : " + cosKey);
		
		Users kakaoUser = Users.builder().userid(kakaoProfile.getKakao_account().getEmail())
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		// 기존 가입자 확인
		Users originUser = userService.회원찾기(kakaoUser.getUserid());
		if(originUser.getUserid() ==null) {
			// 기존 가입 없으면 회원가입
			userService.회원가입(kakaoUser);
		}
		// 로그인
		System.out.println("이미 회원입니다. 자동 로그인을 진행합니다.");
		Authentication autentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUserid(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(autentication);
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";	// 이 경로 아래 파일을 리턴함, yml설정으로 jsp 생략(@컨트롤러)
	}
}
