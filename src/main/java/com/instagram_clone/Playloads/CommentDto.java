package com.instagram_clone.Playloads;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class CommentDto {

	private long commentId;
	
	private String commentContent;
	

    
    private UserDto user;

    
    private PostDto post;

   // private CommentDto parentComment;

   
    private List<PostCommentDto> replies = new ArrayList<>();

	
	
	private LocalDateTime createdAt;
	
	
}
