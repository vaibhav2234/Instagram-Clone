package com.instagram_clone.ExceptionHandler;

import lombok.Data;

@Data
public class RoleException extends Exception{

	public RoleException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
