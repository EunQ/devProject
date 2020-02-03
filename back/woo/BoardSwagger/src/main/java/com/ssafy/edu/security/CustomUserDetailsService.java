package com.ssafy.edu.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.service.MemberServiceImple;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	//@Autowired
	//private MemberJpaRepo memberJpaRepo;
	
	@Autowired
	private MemberServiceImple memberService;
	
	private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("CustomUserDetail username = "+username);
		Member member = memberService.getMemberByID(username);
		return member;
	}
	
	
}
