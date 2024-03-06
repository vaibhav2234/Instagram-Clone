package com.instagram_clone.Playloads;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.instagram_clone.model.Comments;
import com.instagram_clone.model.Image;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class PostDto {

	private long postId;
	
	private String location;
	
	private String caption;
	
	private LocalDateTime createAt;
	
	
	private  UserDto user;
	
	
	private List<ImageDto> image;
	
	
	private List<CommentDto> comments=new ArrayList(); 
	
	private Set<UserDto>likedByUsers=new HashSet<>();
	
	
}
