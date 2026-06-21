package com.lrn.delivery_tracking.security;

import java.util.Collection;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lrn.delivery_tracking.entity.Role;

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String email;
	private final String password;
	private final Set<Role> roles;
	private final  boolean enabled;
	
	public CustomUserDetails(Long id, String email, String password, Set<Role> roles, boolean enabled) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).toList();
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
