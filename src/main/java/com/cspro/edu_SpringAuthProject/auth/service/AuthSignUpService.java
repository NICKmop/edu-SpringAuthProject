package com.cspro.edu_SpringAuthProject.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cspro.edu_SpringAuthProject.auth.entity.UserEntity;
import com.cspro.edu_SpringAuthProject.auth.mapper.UserRepository;
import com.cspro.edu_SpringAuthProject.auth.object.dto.SignInDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthSignUpService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void signUp(SignInDto signInDto) {
		// form data login
		Boolean isExist = userRepository.existsByUsername(signInDto.getUsername());
		
		if(isExist) {
			System.out.println("already exist User!!!!!");
			return;
		}
		
		 // 권한 관련 추후 설정
		UserEntity userEntity = UserEntity
				.builder()
				.username(signInDto.getUsername())
				.password(bCryptPasswordEncoder.encode(signInDto.getPassword()))
				.role("ROLE_ADMIN")
				.build();
		
		userRepository.save(userEntity);
	}
}
