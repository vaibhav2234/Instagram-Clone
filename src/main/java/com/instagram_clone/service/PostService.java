package com.instagram_clone.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.model.Post;

public interface PostService {
	
	public PostDto createPost(Post post,String tokeen ,MultipartFile[] file)throws PostException,UserException,IOException;
	
	public PostDto getPostById(long postid) throws PostException;
	
	public  List<PostDto> getAllPosts() throws PostException;
	
	public String deletePostByUser(long postid) throws PostException;
	
	public PostDto updatePost(long postid ,Post updatePost)throws PostException;
	
	
	public PostDto likedByUser(long userid, long postid)throws PostException,UserException;
	
	public PostDto unLikedPostByUser(long userid ,long postid ) throws PostException,UserException;
	
	public List<PostDto> getPostOfOtherUser(long userid ,long postid) throws PostException,UserException;
	
	


}

