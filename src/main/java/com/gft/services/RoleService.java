package com.gft.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gft.enums.RoleList;
import com.gft.model.Role;
import com.gft.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {
	
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Optional<Role> getByRoleName(RoleList roleName){
		return roleRepository.findByRoleName(roleName);
	}
	
}
