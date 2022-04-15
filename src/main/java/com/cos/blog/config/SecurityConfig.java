package com.cos.blog.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/* 로그인 실패 핸들러 */
	@Bean
	public LoginAuthFailureHandler loginFailHandler(){
		return new LoginAuthFailureHandler();
	}

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}


	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()	// csrf 토근 비활성화
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**", "/css/**", "/image/**", "/java/**", "/sql/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin() 
			.usernameParameter("userid")
			.loginPage("/auth/loginForm")
			.failureHandler(loginFailHandler()) // 로그인 실패 핸들러
			.loginProcessingUrl("/auth/loginProc") 
			.defaultSuccessUrl("/");
			//.failureUrl("로그인 실패 후 요청페이지")
		
	}

}
