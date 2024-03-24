package com.instagram_clone.ServiceImpl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.instagram_clone.ExceptionHandler.ImageException;
import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.Repository.ImageRepo;
import com.instagram_clone.Repository.PostRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Image;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.User;
import com.instagram_clone.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ImageRepo imageRepo;

	@Autowired

	private PostRepo postRepo;

	@Autowired
	@Lazy
	private PostServiceImpl postServiceImpl;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String userUploadImage(long userid, MultipartFile file) throws UserException, IOException {

		Blob blob = null;
		Image image = new Image();
		byte[] context = file.getBytes();
		String filename = file.getOriginalFilename();
		
		String randomid= UUID.randomUUID().toString();
		String filename1 =randomid.concat(filename.substring(filename.lastIndexOf(".")));
			
     

		image.setImagename(filename1);
		image.setImagetype(file.getContentType());

		try {
			blob = new SerialBlob(context);
			image.setImagecontent(blob);
		} catch (SerialException e) {
			String message = e.getMessage();
			new SerialException(message);
		} catch (SQLException e) {
			String message = e.getMessage();
			new SQLException(message);
		}

		User user = userRepo.findById(userid)
				.orElseThrow(() -> new UserException("User not found with uerid : " + userid));
	

		String imageurl = ServletUriComponentsBuilder.fromCurrentContextPath().path("user-image/")
				.path(file.getOriginalFilename()).toUriString();
		image.setImagelink(imageurl);
		user.setImage(image);

		userRepo.saveAndFlush(user);

		return "File uploaded successfully with filename : " + filename;
	}

	@Override
	public PostDto postUploadImage(long postid, MultipartFile[] file) throws PostException, IOException {

		Blob blob = null;
		List<Blob> blobs = new ArrayList<>();
		try {

			for (MultipartFile files : file) {
				blob = new SerialBlob(files.getBytes());
				blobs.add(blob);
			}
		} catch (SerialException e) {
			String message = e.getMessage();
			new SerialException(message);
		} catch (SQLException e) {
			String message = e.getMessage();
			new SQLException(message);
		}

		Optional<Post> post1 = postRepo.findById(postid);

		if (post1.isEmpty()) {
			throw new PostException("Post is not found with postid :  " + postid);
		}
		Post post = post1.get();

		List<Image> images = new ArrayList<>();
		int count = 0;

		for (MultipartFile f : file) {
			String filename =f.getOriginalFilename();
			 String randomid =UUID.randomUUID().toString();
			 String filename1 =randomid.concat(filename.substring(filename.lastIndexOf(".")));
			Image image = new Image(f.getContentType(),filename1, blobs.get(count));

			images.add(image);
			count++;

		}

		post.setImage(images);

		Post savedPost = postRepo.saveAndFlush(post);

		PostDto postDto = mapper.map(savedPost, PostDto.class);
		return postDto;
	}

	
	@Override
	public byte[] getImageByImageName(String imageName) throws ImageException, SQLException {

		Image image = imageRepo.findByImagename(imageName)
				.orElseThrow(() -> new ImageException("image not found with image name : " + imageName));

		Blob blob = image.getImagecontent();

		int imageLength = (int) blob.length();

		byte[] bytes = blob.getBytes(1, imageLength);

		return bytes;
	}

}
