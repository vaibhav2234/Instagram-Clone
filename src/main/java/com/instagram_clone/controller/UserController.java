package com.instagram_clone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.ServiceImpl.UserServiceImpl;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"*"},methods = {RequestMethod.POST,RequestMethod.PUT})
public class UserController {
	
  Logger logger =LoggerFactory.getLogger(UserController.class);	
	@Autowired
	private  UserServiceImpl userServiceImpl;
	
	@PostMapping
	public ResponseEntity<ResponseUserDto> registerUser( @Valid @RequestBody User user
			
			) throws UserException
	{
	  ResponseUserDto createUser = userServiceImpl.createUser(user);
		
		return ResponseEntity.ok()
				.body(createUser);
		
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	//@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<ResponseUserDto>getOneUser(@PathVariable long id) throws UserException
	{
		ResponseUserDto user = userServiceImpl.getUserById(id);
		
		return ResponseEntity.ok()
				.body(user);
	}
	
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.deleteUser(id));
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseUserDto> updateUser(@RequestBody User user ,@PathVariable long id ) throws UserException
	{
	    return ResponseEntity.ok()
	    		.body(userServiceImpl.updateUser(user, id));
	}
	
	@GetMapping
	public ResponseEntity<List<ResponseUserDto>> getAllUser() throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.getAllUser());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/searchByKey")
	public ResponseEntity<List<ResponseUserDto>>getSearchedUsers(@RequestParam("key") String key) throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.searchUsers(key));
	}
	
	@GetMapping("/searchByUsername/{username}")
	public ResponseEntity<ResponseUserDto> getUserByUsername(@PathVariable String username) throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.getUserByUsername(username));
	}
	
	@GetMapping("/followers")
	public ResponseEntity<List<UserDto>>getAllFollowers() throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.followers());
	}
	
	@GetMapping("/followings")
	public ResponseEntity<List<UserDto>>getAllFolloeings() throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.folllowings());
	}
	
	@GetMapping("/roles/{id}")
	public ResponseEntity<List<Role>>getAllUsersRoles(@PathVariable long id) throws UserException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.getAllUsersRoles(id));
	}
	
	@GetMapping("/{userid}/saved-posts")
	public ResponseEntity<List<PostDto>> getAllSavedPostsByUser(@PathVariable long userid)throws UserException
	{
		List<PostDto> posts= userServiceImpl.getAllSavedPostByUser(userid);
		
		return ResponseEntity.ok()
				.body(posts);
	}
	
	@PutMapping("/{userid}/post/{postid}")
	public ResponseEntity<ResponseUserDto>savePostByUser(@PathVariable long userid,@PathVariable long postid) throws UserException, PostException
	{
		return ResponseEntity.ok()
				.body(userServiceImpl.savePostbyUser(userid, postid));
	}
	
	@PutMapping("/{userid}/unsave-post/{postid}")
	public ResponseEntity<String> unsavePostByUser(@PathVariable long userid ,@PathVariable long postid ) throws UserException, PostException{
		
		return ResponseEntity.ok()
				.body(userServiceImpl.unSavedPostByUser(userid, postid));
		
	}
	
	
}
