package com.instagram_clone.model;

import java.sql.Blob;
import java.time.LocalDateTime;

import com.instagram_clone.Playloads.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name =  "Stories")
public class Story {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long storyId;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride( name = "id" ,column = @Column( name = "user_id")),
			@AttributeOverride( name = "email" ,column = @Column( name = "user_email"))
	})
	private UserDto user;
	
	@NotNull
	@OneToOne
	private Image image;
	
	private String caption;
	
	
	private LocalDateTime timestamp;
	
	

}
