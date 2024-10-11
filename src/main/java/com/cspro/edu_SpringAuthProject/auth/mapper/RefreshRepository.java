package com.cspro.edu_SpringAuthProject.auth.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cspro.edu_SpringAuthProject.auth.entity.RefreshEntity;

import java.util.List;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
	List<RefreshEntity> findByUsername(String username);
	
	boolean existsByRefresh(String refresh);
	
	@Transactional
	void deleteByRefresh(String refresh);
}
