package com.gft.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gft.jwt.AuthenticationService;
import com.gft.services.UserDetailsServiceImpl;

public class FilterAuthentication extends OncePerRequestFilter{
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FilterAuthentication.class);
	
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = getToken(request);
			
			if(token != null && authenticationService.validateToken(token)) {
				String userName = authenticationService.getUserNameFromToken(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");
		}
		return null;
	}

}
