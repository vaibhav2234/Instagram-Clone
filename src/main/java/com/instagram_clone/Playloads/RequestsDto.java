package com.instagram_clone.Playloads;

import lombok.Data;

@Data
public class RequestsDto {
	
private long requestid;
	
	private long requestUserId;
	
	private long followUserId;
	
	private String requestStatus;

}
