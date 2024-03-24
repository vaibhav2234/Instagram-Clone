package com.instagram_clone.service;

import java.util.List;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

public interface UserService {
	
	public ResponseUserDto createUser(ResponseUserDto user) throws UserException;
	
	public ResponseUserDto getUserById(long id) throws UserException;
	
	
	
	public String deleteUser(long id) throws UserException;
	
	public ResponseUserDto updateUser(User updateUser ,long id)throws UserException;
	
	public ResponseUserDto getUserByUsername(String username)throws UserException;
	
	public List<UserDto> followers()throws UserException;
	
	public List<UserDto> folllowings()throws UserException;
	
	public String followUser(long reqUserId,long followUserId)throws UserException;
	
	public String unFollowUser(long reqUserId,long FollowUserId)throws UserException;
	
    public List<ResponseUserDto> getAllUser()throws UserException;
    
    public List<ResponseUserDto> searchUsers(String query)throws UserException;
    
    public ResponseUserDto savePostbyUser(long userid,long postid) throws UserException ,PostException;
	
    public List<Role> getAllUsersRoles(long id)throws UserException;
	
    public List<PostDto> getAllSavedPostByUser(long id) throws UserException;
    
    public ResponseUserDto getUserByUsernameEmailMobile(String username)throws UserException;	
    
    public String unSavedPostByUser(long userid ,long postid) throws UserException ,PostException;
   

}
