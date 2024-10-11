package com.cspro.edu_SpringAuthProject.auth.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.cspro.edu_SpringAuthProject.auth.entity.RefreshEntity;
import com.cspro.edu_SpringAuthProject.auth.mapper.RefreshRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshRepository refreshRepository;
	
	@Transactional
	public void saveRefresh(String username, Integer expires, String refresh) {
		RefreshEntity refreshEntity = RefreshEntity.builder()
				.username(username)
				.refresh(refresh)
				.expiration(new Date(System.currentTimeMillis() + expires * 1000L).toString())
				.build();
		
		refreshRepository.save(refreshEntity);
	}
}
