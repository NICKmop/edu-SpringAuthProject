package com.cspro.edu_SpringAuthProject.auth.object.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cspro.edu_SpringAuthProject.auth.entity.UserEntity;

public class CustomUserDetails implements UserDetails{
	
	private final UserEntity userEntity;
	
	public CustomUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	// ROLE SETTER	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userEntity.getRole();
			}
		});
		
		collection.forEach(item -> {
			System.out.println(item.getAuthority());
		});
		
		return collection;
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
