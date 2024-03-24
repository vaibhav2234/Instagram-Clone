package com.instagram_clone.Playloads;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data

public class RequestsDto {
	
private long requestid;
	
	private long requestUserId;
	
	private long followUserId;
	
	private String requestStatus;
	
	@JsonIgnore
	private Set<ResponseUserDto> users;

}
