package com.instagram_clone.Security.JWTConfig;

import java.io.IOException;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.instagram_clone.UserServiceDetails.CustomUserServiceDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;
	
	Logger logger =LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtHelper jwtHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token =null;
		
		String username=null;
		
		if(header!=null && header.startsWith("Bearer"))
		{
			token =header.substring(7);
			
			try 
			{
				username = jwtHelper.getUsernameFromToken(token);
				
			}catch(IllegalArgumentException e) {
				
				throw new IllegalIdentifierException("Username id invalid :"+username);
			}
			catch (MalformedJwtException e) {
			   throw new MalformedJwtException("Invalid Jwt Token :" +token);
			}
			catch (ExpiredJwtException e) {
				throw new ExpiredJwtException(null,null,"The JWT token is expired !! Please Login Again");
			}
		}
		else
		{
			logger.info("Header is empty (or) Entered token does not contain the");
			
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails user = customUserServiceDetails.loadUserByUsername(username);
              logger.info("Username of user = "+ user.getUsername() + "Password of user = "+ user.getPassword());			
			logger.info("The authorities of the user is " + user.getAuthorities().size() + " " + user.getAuthorities().toString());
			
			if(jwtHelper.validateToken(token, user))
			{
				try
				{
				   
					UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					
					
				}catch (Exception e) {
				    new Exception("Jwt token id invalid !!");
				}
			}
			else
			{
				logger.info(" Enter the valid Token");
				
			}
		}
		else
		{
			logger.info("Username is null");
		}
		
		doFilter(request, response, filterChain);
		
		logger.info("jwt process is completed");
	}

}
