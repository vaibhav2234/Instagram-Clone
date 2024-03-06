package com.instagram_clone.service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;

public interface Request {
	
	public ResponseUserDto RequestedUser(long requestUserId ,long followUserId) throws  UserException,RequestHandler;

}
 