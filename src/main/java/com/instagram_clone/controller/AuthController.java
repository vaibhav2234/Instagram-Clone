package com.instagram_clone.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram_clone.Security.JWTConfig.JwtHelper;
import com.instagram_clone.UserServiceDetails.CustomUserServiceDetails;
import com.instagram_clone.model.AuthRequest;
import com.instagram_clone.model.AuthResponse;

@RestController
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;
	
	Logger logger =LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse>loginUser(@RequestBody AuthRequest authRequest)
	{
		authenticate(authRequest.getUsername() ,authRequest.getPassword());
		
		String token =null;
		logger.info("Enter into the Auth Controller class = + +++++++++++++++++++++++++++++++++++++++++++++0");
		logger.info("Username from  Auth Controller class ================== " + authRequest.getUsername());
		UserDetails user = customUserServiceDetails.loadUserByUsername(authRequest.getUsername());
		
		logger.info("User from the AuthController  + + + =" + user.getPassword()+ "  ::::"+user.getUsername() );
		
		try
		{
			token = jwtHelper.generateToken(user);
		}
		catch (Exception e) {
			new Exception("Invalid Username and password");
		}
		
		AuthResponse authResponse =new AuthResponse();
		
		authResponse.setToken(token);
		
		return ResponseEntity.ok()
				.body(authResponse);
		
		
		
	}

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(username, password);
		
		try 
		{
			
			Authentication  authentication=authenticationManager.authenticate(authenticationToken);
			logger.info("Enter into the Authentication method from Auth Controller class = + +++++++++++++++++++++++++++++++++++++++++++++0");
		}
		 catch (DisabledException e) {
			throw new DisabledException("Your acoount currently disable. Please Re-Login");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username and Password");
		}
		
	}
	
	

}
