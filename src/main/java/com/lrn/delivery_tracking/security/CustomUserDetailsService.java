package com.lrn.delivery_tracking.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lrn.delivery_tracking.repository.UserRepository;
import com.lrn.delivery_tracking.entity.User;;

@Service
public class CustomUserDetailsService implements UserDetailsService {


	private final UserRepository userRepo;
	
	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CustomUserDetails(user.getId(), user.getEmail(), user.getPasswordHash(), user.getRole().name(), user.isEnabled());
	}

}
