package com.gft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gft.filters.FilterAuthentication;
import com.gft.jwt.JwtEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private JwtEntryPoint jwtEntryPoint;
	
	public SecurityConfig(JwtEntryPoint jwtEntryPoint) {
		this.jwtEntryPoint = jwtEntryPoint;
	}
	
	//------------

	@Bean
	public FilterAuthentication filterAuthentication() {
		return new FilterAuthentication();
	}
	
	//------------
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//------------
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.cors()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/h2/**").permitAll()
			.antMatchers("/v1/auth/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(filterAuthentication(), UsernamePasswordAuthenticationFilter.class);
		
		http.headers().frameOptions().disable();
		
		return http.build();
	}
	
	
	
}
