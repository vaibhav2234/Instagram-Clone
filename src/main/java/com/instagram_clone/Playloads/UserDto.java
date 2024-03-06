package com.instagram_clone.Playloads;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import com.instagram_clone.model.Image;
import com.instagram_clone.model.Role;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Embeddable
public class UserDto {
	
	@Cascade(value = {CascadeType.ALL})
	private long id;
	
	private String fullName;
	
	private String username;
	private String email;
	
	@Lob
	private Blob userImage;
	
	private LocalDateTime createDate;                 
	
	
	
	
	

}
                                