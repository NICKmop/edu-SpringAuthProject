package com.cspro.edu_SpringAuthProject.auth.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspro.edu_SpringAuthProject.auth.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
}
