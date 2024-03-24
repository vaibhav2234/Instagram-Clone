package com.instagram_clone.service;

import com.instagram_clone.ExceptionHandler.CommentException;
import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.CommentDto;
import com.instagram_clone.Playloads.PostCommentDto;

public interface CommentService {
	
	public PostCommentDto createComment(CommentDto commentDto ,long userid ,long postid,long commentid) throws UserException ,PostException,CommentException;

}
