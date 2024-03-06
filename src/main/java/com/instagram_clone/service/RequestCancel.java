package com.instagram_clone.service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;

public interface RequestCancel {
	
	public String RequestCanceled(long requstedUserId,long followUserId) throws UserException ,RequestHandler;

}
