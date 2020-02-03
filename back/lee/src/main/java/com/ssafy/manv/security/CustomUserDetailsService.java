package com.ssafy.manv.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.manv.entity.Member;
import com.ssafy.manv.service.MemberService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	//@Autowired
	//private MemberJpaRepo memberJpaRepo;
	
	@Autowired
	private MemberService memberService;
	private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("CustomUserDetail username = "+username);
		Member member = memberService.findByMemberId(username);
		return member;
	}
	
	
}
