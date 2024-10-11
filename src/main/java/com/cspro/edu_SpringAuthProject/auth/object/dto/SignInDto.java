package com.cspro.edu_SpringAuthProject.auth.object.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter @Setter
public class SignInDto {
	private String username;
	private String password;
	
	public SignInDto(String username, String password) {
		this.username = username;
		this.password = password;
	} 
}
