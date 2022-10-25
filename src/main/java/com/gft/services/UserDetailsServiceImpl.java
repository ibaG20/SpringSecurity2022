package com.gft.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.model.MainUser;
import com.gft.model.UserModel;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserModel user = userService.findByEmail(username);
		return MainUser.build(user);
	}
	

}
