package com.instagram_clone.model;

import com.instagram_clone.Playloads.UserDto;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="id", column = @Column(name="user_id")),
		@AttributeOverride(name="email" ,column = @Column(name="user_email"))
	})
	private UserDto user;
	
	private String content;
	
	@Embedded
	@ElementCollection
	private Set<UserDto> likeByUsers=new HashSet<>();
	
	@ManyToOne
	private Post post;
	

	
	
	private LocalDateTime createdAt;
}
