package com.cspro.edu_SpringAuthProject.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cspro.edu_SpringAuthProject.auth.domain.UserDTO;
import com.cspro.edu_SpringAuthProject.auth.domain.UserEntity;
import com.cspro.edu_SpringAuthProject.auth.mapper.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void sign(UserDTO dto) {
        UserEntity entity = new UserEntity();
        
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setNickname(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setRole("ADMIN");

        userRepository.save(entity);
    }
}
