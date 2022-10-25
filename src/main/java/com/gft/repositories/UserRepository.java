package com.gft.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>{
	
	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByName(String name);
	boolean existsByName(String name);
	Page<UserModel> findAll(Pageable pageable);

}
