package com.instagram_clone.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	
	private String message;
	
	private String description;
	
	private LocalDateTime timestamp;
	


}
