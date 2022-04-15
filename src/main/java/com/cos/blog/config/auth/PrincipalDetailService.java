package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.Users;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException{
		Users principal=userRepository.findByUserid(userid).orElseThrow(()->{
			return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.:" + userid);
		});
		return new PrincipalDetail(principal);
		}
	
}
