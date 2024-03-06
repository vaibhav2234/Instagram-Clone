package com.instagram_clone.service;

import java.util.List;

import com.instagram_clone.ExceptionHandler.RoleException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

public interface RoleService {
	
	public Role update(long userid ,long roleid) throws RoleException,UserException; 
	
	public Role create(Role role) throws RoleException;
	
	public List<User> getUsersByRole(long roleid)  throws RoleException;
	
	public String deleteRole(long roleid)throws RoleException;
	
	public Role UpdateRole(Role role ,long roleid) throws RoleException;
	
	
}
