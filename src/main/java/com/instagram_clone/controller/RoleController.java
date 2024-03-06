package com.instagram_clone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram_clone.ExceptionHandler.RoleException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.ServiceImpl.RoleServiceImpl;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

import jakarta.validation.constraints.Positive;

@RestController
public class RoleController {
	
	@Autowired
	private RoleServiceImpl roleServiceImpl;

	@PostMapping("/role")
	public ResponseEntity<Role> createRole(@RequestBody Role role) throws RoleException
	{
		return ResponseEntity.ok()
				.body(roleServiceImpl.create(role));
		
	}
	
	@GetMapping("/role/{roleid}")
	public ResponseEntity<List<User>>getAllUsersByRole(@PathVariable long roleid) throws RoleException
	{
		return ResponseEntity.ok()
				.body(roleServiceImpl.getUsersByRole(roleid));
	}
	
	@PutMapping("/role/{roleid}")
	public ResponseEntity<Role>updateRole(@RequestBody Role role ,long roleid)throws RoleException
	{
		return ResponseEntity.ok()
				.body(roleServiceImpl.UpdateRole(role, roleid));
	}
	
   @PutMapping("/user/{userid}/role/{roleid}") 
   public ResponseEntity<Role>updateUserRole(@PathVariable long userid ,@PathVariable long roleid ) throws RoleException, UserException
   {
	   return ResponseEntity.ok()
			   .body(roleServiceImpl.update( userid, roleid));
   }
   

}
