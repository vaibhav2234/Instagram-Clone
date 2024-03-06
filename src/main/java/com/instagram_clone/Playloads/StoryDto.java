package com.instagram_clone.Playloads;

import java.time.LocalDateTime;

import com.instagram_clone.model.Image;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoryDto {

    private long storyId;
	
	
	private UserDto user;
	
	
	//private ImageDto image;
	
	private String caption;
	
	private LocalDateTime timestamp;
	
}
