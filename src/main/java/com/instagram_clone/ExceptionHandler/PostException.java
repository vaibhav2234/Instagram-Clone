package com.instagram_clone.ExceptionHandler;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PostException extends Exception{

	public PostException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
    

}
