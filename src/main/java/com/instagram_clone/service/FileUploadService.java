package com.instagram_clone.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;

public interface FileUploadService {
	
	public String userUploadImage(long userid,MultipartFile file) throws UserException,IOException;
	
	public PostDto postUploadImage(long postid,MultipartFile[] file) throws PostException,IOException;
	
    

}
