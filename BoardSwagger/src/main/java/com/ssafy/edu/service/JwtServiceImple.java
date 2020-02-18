package com.ssafy.edu.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImple implements IJwtService {

	private static final String SALT =  "ssafy";
	
    private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효

	@Override
	public String create(String id, int roles) {
		Claims claims = Jwts.claims().setSubject(id);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SALT) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

	@Override
	public String getMemberId(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getSubject();
	}

	@Override
	public boolean isUsable(String jwt) {
		try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
	}
	
}
