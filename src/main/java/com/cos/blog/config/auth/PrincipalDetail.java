package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.Users;

import lombok.Getter;

@Getter
public class PrincipalDetail implements UserDetails{
	private Users user;
	public PrincipalDetail(Users user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList 의 부모에 콜렉션타입이 있음
		// GrantedAuthority가 가진 유일한 메소드 getAuthority()를 람다식으로
		collectors.add(()->{return "ROLE_" + user.getRoles();});	// 규칙상 ROLE_USER 형태로 리턴해야함
		return collectors;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	public String getUserid() {
		return user.getUserid();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료되었는지 리턴, true: 만료안됨, 사용가능
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠겼는지, true: 안잠김, 사용가능
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비번 만료되었는지, true : 사용가능
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화, true : 사용가능
		return true;
	}
}
