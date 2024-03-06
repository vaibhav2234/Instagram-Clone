package com.instagram_clone.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AuthRequest {
	
	private String username;
	
	private String password;
	

}
