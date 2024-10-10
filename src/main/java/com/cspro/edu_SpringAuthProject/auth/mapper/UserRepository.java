package com.cspro.edu_SpringAuthProject.auth.mapper;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspro.edu_SpringAuthProject.auth.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByUsername(String email);
}
