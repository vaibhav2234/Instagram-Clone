package com.instagram_clone.model;

import org.springframework.http.MediaType;

import lombok.Data;

@Data
public class ImageResponse {

 	 private MediaType mediaType;
 	 
 	 private byte[] bytes;
}
