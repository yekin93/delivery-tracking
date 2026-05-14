package com.lrn.delivery_tracking.security;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String email;
	private final String password;
	private final String role;
	private final  boolean enabled;
	
	public CustomUserDetails(Long id, String email, String password, String role, boolean enabled) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public Long getId() {
		return id;
	}

	
	public boolean isEnabled() {
		return enabled;
	}
}
