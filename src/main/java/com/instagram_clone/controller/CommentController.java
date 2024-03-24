package com.instagram_clone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram_clone.ExceptionHandler.CommentException;
import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.CommentDto;
import com.instagram_clone.Playloads.PostCommentDto;
import com.instagram_clone.ServiceImpl.CommentServiceImpl;
import com.instagram_clone.model.Post;

@RestController
public class CommentController {
	
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	
	@PostMapping("/post/{postid}/user/{userid}/comment/{commentid}")
	public ResponseEntity<PostCommentDto>createComment(@RequestBody CommentDto comment,
			@PathVariable long postid ,
			@PathVariable long userid,
			@PathVariable (required = false) long commentid) throws UserException, PostException,CommentException
	
	{
		PostCommentDto comment2 =null;
		if(commentid ==0)
		{
			 comment2 = commentServiceImpl.createComment(comment, userid, postid, commentid);
		}
		else
		{
			comment2 =commentServiceImpl.createComment(comment, userid, postid, commentid);
		}
		
		return ResponseEntity.ok()
				.body(comment2);
		
	}
	
	
	@GetMapping("/comment/{id}")
	public ResponseEntity<PostCommentDto>getCommentById(@PathVariable long id)throws CommentException
	{
		return ResponseEntity.ok()
				.body(commentServiceImpl.getCommentByCommentid(id));
	}
	
	@DeleteMapping("post/{postid}/user/{userid}/parantComment/{parentCommentid}/comment/{id}")
	public ResponseEntity<String>deleteComment(@PathVariable long postid,
			@PathVariable long userid,
			@PathVariable long parentCommentid,
			@PathVariable long id)
	{
		return ResponseEntity.ok()
				.body(commentServiceImpl.deleteComment(postid ,userid,parentCommentid,id));
	}
	
	
	

}
