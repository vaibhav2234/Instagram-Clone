package com.instagram_clone.UserServiceDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.model.User;

public class UserServiceDetails implements UserDetails {
	
	private ResponseUserDto user;
	
	public UserServiceDetails(ResponseUserDto user)
	{
		this.user=user;
	}

	Logger logger=LoggerFactory.getLogger(UserServiceDetails.class);
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<SimpleGrantedAuthority> roles = user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
		 logger.info("the user authorities from UserServiceDetails is " +roles);
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail() ;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
