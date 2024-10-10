package com.cspro.edu_SpringAuthProject.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cspro.edu_SpringAuthProject.auth.domain.UserEntity;
import com.cspro.edu_SpringAuthProject.auth.mapper.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findByUsername(username).orElseThrow();

        UserDetails userDetails = User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRole())
                .build();

        return userDetails;
    }
}
