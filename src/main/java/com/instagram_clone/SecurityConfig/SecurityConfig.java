package com.instagram_clone.SecurityConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.instagram_clone.Security.JWTConfig.JwtAuthenticationEntryPoint;
import com.instagram_clone.Security.JWTConfig.JwtAuthenticationFilter;
import com.instagram_clone.Security.JWTConfig.JwtHelper;
import com.instagram_clone.UserServiceDetails.CustomUserServiceDetails;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebMvc
public class SecurityConfig {
	
	@Autowired
	private CustomUserServiceDetails customUserServiceDetails;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	
	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http
		.csrf((crsf)->crsf.disable())
		.cors((cors)->cors.configurationSource(configurationSource()))
		.authorizeHttpRequests((auth)->auth.requestMatchers("/user/**").permitAll())
		.authorizeHttpRequests((auth)->auth.requestMatchers("/user-image/**").permitAll())
		.authorizeHttpRequests((auth)->auth.requestMatchers("/post/*/images").permitAll())
		.authorizeHttpRequests((auth)->auth.requestMatchers("/login").permitAll().anyRequest().authenticated())
		.sessionManagement((session)->session.sessionCreationPolicy( SessionCreationPolicy.STATELESS))
		.exceptionHandling((exception)->exception.authenticationEntryPoint(authenticationEntryPoint));
		
		http.addFilterAfter(authenticationFilter,UsernamePasswordAuthenticationFilter.class );
		
	
		
		return http.build();
	}
	
	@Bean 
	public DaoAuthenticationProvider authenticationProvider(){
		 DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
		 
		 daoAuthenticationProvider.setUserDetailsService(customUserServiceDetails);
		 daoAuthenticationProvider.setPasswordEncoder(encoder());
		 
		 return daoAuthenticationProvider;
		
	}
	
	@Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
   {
	   return config.getAuthenticationManager();
   }
	
	@Bean
	CorsConfigurationSource configurationSource()
	{
		CorsConfiguration configuration =new CorsConfiguration();
		
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("POST","GET","PUT","OPTION","DETELE"));
	    configuration.setMaxAge((long) 3600);
	    configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
	    
	    UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
		
			
		
		
		return source;
		
	}
	

}
