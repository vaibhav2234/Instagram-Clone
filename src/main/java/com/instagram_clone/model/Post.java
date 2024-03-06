package com.instagram_clone.model;

import java.sql.Blob;
import java.time.LocalDateTime;

import com.instagram_clone.Playloads.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.*;

import org.hibernate.annotations.Cascade;

@Entity
@Table( name="Posts")
@Data

public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postId;
	
	private String location;
	
	private String caption;
	
	private LocalDateTime createAt;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name ="id" ,column = @Column(name="user_id")),
		@AttributeOverride(name="email",column = @Column(name="user_email")),
		
	})
	private  UserDto user;
	
	@OneToMany(cascade =CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="post_id")
	private List<Image> image;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Comments> comments=new ArrayList(); 
	
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "Post_likedByUsers",joinColumns = @JoinColumn(name="user_id"))
	private Set<UserDto>likedByUsers=new HashSet<>();
	
	
}
