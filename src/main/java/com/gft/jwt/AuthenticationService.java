package com.gft.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class AuthenticationService {
	
	private final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Value("${seguranca.jwt.expiration}")
	private String expiration;
	
	@Value("${seguranca.jwt.secret}")
	private String secret;
	
	@Value("${seguranca.jwt.issuer}")
	private String issuer;
	
	private Algorithm createAlgorithm() {
		return Algorithm.HMAC256(secret);
	}
	
	//-------
	
	public String generateToken(Authentication authentication) {
		
		UserDetails mainUser = (UserDetails) authentication.getPrincipal();
		logger.error(mainUser.getUsername());
		
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(expirationDate)
				.withSubject(mainUser.getUsername().toString())
				.sign(this.createAlgorithm());
	}
	
	//-------
	
	public String getUserNameFromToken(String token) {
		
		String subject = JWT.require(this.createAlgorithm()).withIssuer(issuer).build().verify(token).getSubject();
		
		return String.format(subject);
	}
	
	//-------
	
	public boolean validateToken(String token) {
		
		try {
			JWT.require(this.createAlgorithm()).build().verify(token);
			return true;
		}catch(IllegalArgumentException e) {
			logger.error("Misinformed token");
		}
		
		return false;
	}

}
