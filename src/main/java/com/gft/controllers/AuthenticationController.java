package com.gft.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.dtos.AuthenticationDTO;
import com.gft.dtos.NewUser;
import com.gft.dtos.TokenDTO;
import com.gft.enums.RoleList;
import com.gft.exceptions.Message;
import com.gft.jwt.AuthenticationService;
import com.gft.model.Role;
import com.gft.model.UserModel;
import com.gft.services.RoleService;
import com.gft.services.UserService;

@RestController
@RequestMapping("v1/auth")
public class AuthenticationController {
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final RoleService roleService;
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationManagerBuilder authenticationManagerBuilder,
			PasswordEncoder passwordEncoder, UserService userService, RoleService roleService,
			AuthenticationService authenticationService) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.roleService = roleService;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationDTO loginUser, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Review the entries and try again"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = authenticationService.generateToken(authentication);
			TokenDTO jwtDto = new TokenDTO(jwt);
			return new ResponseEntity<>(jwtDto, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new Message("Review your Entries"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	public ResponseEntity<Object> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Review the entries and try again"), HttpStatus.BAD_REQUEST);
		}
		
		UserModel user = new UserModel(newUser.getName(), newUser.getEmail(), 
				passwordEncoder.encode(newUser.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleList.ROLE_USER).get());
		
		if(newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleName(RoleList.ROLE_ADMIN).get());
		}
		
		user.setRoles(roles);
		userService.saveUser(user);
		
		return new ResponseEntity<>(new Message("Registration Successful"), HttpStatus.CREATED);
	}
	
	

}
