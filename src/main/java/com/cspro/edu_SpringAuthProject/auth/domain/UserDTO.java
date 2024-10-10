package com.cspro.edu_SpringAuthProject.auth.domain;

import lombok.Data;

@Data
public class UserDTO {
	private String username;
    private String password;
    private String role;
    private String nickname;
    private String phone;
}
