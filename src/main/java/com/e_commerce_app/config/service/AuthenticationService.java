package com.e_commerce_app.config.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_commerce_app.entity.User;
import com.e_commerce_app.repository.UserRepository;


@Service("authenticationService")
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Check user by username
	 */
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userId);
		if(user == null)
			throw new UsernameNotFoundException("Invalid username or password.");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
	}
	
	/**
	 * Get user role
	 * @param user
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthority(User user) {
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
	    return grantedAuths;
	}
}
