package com.instagram_clone.service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;

public interface RequestConfirm {
	
	public ResponseUserDto RequestConfirmed(long FollowUserId ,long requestUserId ) throws UserException ,RequestHandler;
}
