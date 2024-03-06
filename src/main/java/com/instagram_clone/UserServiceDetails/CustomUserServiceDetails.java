package com.instagram_clone.UserServiceDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.ServiceImpl.UserServiceImpl;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

@Service
public class CustomUserServiceDetails implements UserDetailsService {

	
	
	Logger logger =LoggerFactory.getLogger(CustomUserServiceDetails.class);
	
	@Autowired
	@Lazy
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 
		ResponseUserDto user=null;
		try {
			user = userServiceImpl.getUserByUsernameEmailMobile(username);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			String message = e.getMessage();
			new UserException(message);
		}
		 
		List<Role> roles1 = userRepo.getAllUsersRoles(user.getId());
		
		for(Role role :roles1)
		{
			user.getRoles().add(role);
		}
		
		logger.info("Enter into the UserDetailService class = + +++++++++++++++++++++++++++++++++++++++++++++0");
		
		return new UserServiceDetails(user);
	}

}



