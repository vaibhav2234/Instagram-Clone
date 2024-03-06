package com.instagram_clone.Playloads;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.instagram_clone.model.Role;

import lombok.Data;

@Data
public class ResponseUserDto {

	private long id;

	private String fullName;

	private String username;
	private String email;
	private String mobile;
	private String password;
	private Date date;
	
	private String account;

	private String website;

	private String gender;

	private String bio;

	private ImageDto image;

	private Set<Role> roles;
	
	private List<PostDto> posts;
	
	private Set<UserDto> followers;
	
	private Set<UserDto> followings;
	
	private Set<RequestsDto> requestedUsers;

}
