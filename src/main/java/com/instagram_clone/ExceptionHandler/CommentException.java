package com.instagram_clone.ExceptionHandler;

import javax.xml.stream.events.Comment;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentException  extends Exception{
	
	    public CommentException(String message)
	    {
	    	super(message);
	    }

	
}
