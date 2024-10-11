package com.cspro.edu_SpringAuthProject.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String refresh;
	private String expiration;
	
	@Builder
	public RefreshEntity(String username, String refresh, String expiration) {
		this.username = username;
		this.refresh = refresh;
		this.expiration = expiration;
	}
}
