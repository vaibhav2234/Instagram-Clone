package com.instagram_clone.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.ImageException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.ServiceImpl.FileUploadServiceImpl;
import com.instagram_clone.ServiceImpl.UserServiceImpl;
import com.instagram_clone.model.User;
import com.instagram_clone.service.FileUploadService;

@RestController
@CrossOrigin(maxAge = 3600)
public class ImageController {
	
	@Autowired
	private FileUploadServiceImpl fileUploadServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	Logger logger =LoggerFactory.getLogger(ImageController.class);

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/user/{userid}/upload-image")
	public ResponseEntity<String> uploadImageOrVideo(@PathVariable long userid
			,@RequestPart("file") MultipartFile file) throws IOException, UserException
	{
		
		logger.info("enter into the imagecontroller class");
		return ResponseEntity.ok()
				.body(fileUploadServiceImpl.userUploadImage(userid,file));
	}
	
	@CrossOrigin(origins = "*",methods = RequestMethod.GET)
	@GetMapping("/user-image/{imagename}")
	public ResponseEntity<byte[]>getImageByImagename(@PathVariable String imagename) throws ImageException, SQLException
	{
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
			
				.body(fileUploadServiceImpl.getImageByImageName(imagename));
	}
	
	
	
}
