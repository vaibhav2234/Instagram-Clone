package com.instagram_clone.service;

import java.util.Arrays;

import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.model.User;

public interface FollowRequest {
	// If the RequestUser and FollowUser both have the public accounts
	public ResponseUserDto sendRequestToPublicFollowUser( User requestedUser ,User followUser) throws UserException;
	
	public ResponseUserDto followbackRequestToPublicRequestUser( User followUser ,User requestedUser) throws UserException;
	
	//If RequestUser and FollowUser both have the private accounts
	


	public ResponseUserDto confirmRequestByPrivateRequestUser( User followUser ,User requestedUser) throws UserException;
	
	//If RequestUser have Public account and FollowUser have Private account
	
	public ResponseUserDto comfirmRequestByPrivateFollowuser( User requestedUser ,User followUser ) throws UserException;
	
	
	//If RequestUser have Private account and FollowUser have Public account
	
     public ResponseUserDto privateRequestuserSendRequestToPublicFollowuser(User requestedUser ,User followUser) throws UserException;
	
	
	
}


