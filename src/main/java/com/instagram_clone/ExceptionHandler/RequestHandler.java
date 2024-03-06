package com.instagram_clone.ExceptionHandler;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestHandler extends Exception {
	
	public RequestHandler( String message)
	{
		super(message);
		
	}

}
