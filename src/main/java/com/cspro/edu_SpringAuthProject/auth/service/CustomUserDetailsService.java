package com.cspro.edu_SpringAuthProject.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cspro.edu_SpringAuthProject.auth.entity.UserEntity;
import com.cspro.edu_SpringAuthProject.auth.mapper.UserRepository;
import com.cspro.edu_SpringAuthProject.auth.object.dto.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if(userEntity != null) {
			log.info("cutom user service : {}", userEntity);
			return new CustomUserDetails(userEntity);
		}
		throw new UsernameNotFoundException("User Not found!!");
	}
}
