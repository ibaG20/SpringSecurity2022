package com.gft.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.model.UserModel;
import com.gft.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//----X
	
	public UserModel saveUser(UserModel user) {
		
		return userRepository.save(user);
	}
	
	//----X
	
	public boolean existsByUserName(String userName) {
		
		return userRepository.existsByName(userName);
	}
	
	//----X
	
	public UserModel findUserByName(String userName) {
		
		Optional<UserModel> optional = userRepository.findByName(userName);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		return optional.get();
	}
	
	//----X
	
	public UserModel findByEmail(String email) {
		
		Optional<UserModel> optional = userRepository.findByEmail(email);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		return optional.get();
	}
	
	//----X
	
	public UserModel findById(UUID userId) {
		
		Optional<UserModel> optional = userRepository.findById(userId);
		
		if(optional.isEmpty()) {
			throw new RuntimeException("User not found!");
		}
		
		return optional.get();
	}

}
