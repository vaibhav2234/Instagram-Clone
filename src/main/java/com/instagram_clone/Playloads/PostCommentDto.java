package com.instagram_clone.Playloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data

public class PostCommentDto {

	private long commentId;
	
	private String commentContent;
	private UserDto user;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class,property = "commentId")
//	@JsonIdentityReference(alwaysAsId = true)
	//@JsonManagedReference
	private List<PostCommentDto>replies;
	
	
	

}
