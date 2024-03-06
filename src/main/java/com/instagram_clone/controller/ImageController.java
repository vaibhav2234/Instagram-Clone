package com.instagram_clone.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.ServiceImpl.FileUploadServiceImpl;
import com.instagram_clone.ServiceImpl.UserServiceImpl;
import com.instagram_clone.model.User;
import com.instagram_clone.service.FileUploadService;

@RestController
public class ImageController {
	
	@Autowired
	private FileUploadServiceImpl fileUploadServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/user/{userid}/upload-image")
	public ResponseEntity<String> uploadImageOrVideo(@PathVariable long userid
			,@RequestPart("file") MultipartFile file) throws IOException, UserException
	{
		
		
		return ResponseEntity.ok()
				.body(fileUploadServiceImpl.userUploadImage(userid,file));
	}
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/user/{userid}/image")
	public ResponseEntity<byte[]> getImageByUser(@PathVariable long userid) throws UserException, SQLException
	{
		ResponseUserDto user = userServiceImpl.getUserById(userid);
		
		return null;
		
	}
	
	
}
