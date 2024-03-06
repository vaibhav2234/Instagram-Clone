package com.instagram_clone.ServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.Repository.PostRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.Security.JWTConfig.JwtHelper;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.User;
import com.instagram_clone.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private FileUploadServiceImpl fileUploadServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@Autowired
	private JwtHelper jwtHelper;
	@Override
	public PostDto createPost(Post post ,String token,MultipartFile[] file) throws PostException,UserException,IOException{
		String username = jwtHelper.getUsernameFromToken(token);
		 ResponseUserDto user =null;
		try{
		 user = userServiceImpl.getUserByUsernameEmailMobile(username);
		}
		catch (UserException e) {
			String message = e.getMessage();
		  new UserException(message);
		}
		
		
		UserDto userDto = mapper.map(user, UserDto.class);
		post.setUser(userDto);
		
		post.setCreateAt(LocalDateTime.now());
		
		Post savedPost = postRepo.save(post);
		 PostDto savedPostDto = fileUploadServiceImpl.postUploadImage(savedPost.getPostId(),file);
		
		
		return savedPostDto ;
	}
	@Override
	public PostDto getPostById(long postid) throws PostException {
		 Optional<Post> post1 = postRepo.findByPostId(postid);
		 if(post1.isEmpty())
		 {
			 throw new PostException("Post not found with postId : "+postid);
		 }
		 Post post =post1.get();
		 PostDto postDto = mapper.map(post,PostDto.class);
		return postDto;
	}
	
	
	@Override
	public List<PostDto> getAllPosts() throws PostException {
		List<Post> posts = postRepo.findAll();
		if(posts.isEmpty())
		{
			throw new PostException("Post not posted yet !!");
		}
		
		    List<PostDto>postDtos =posts.stream().map((post)->mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	
	@Override
	public String deletePostByUser(long postid) throws PostException {
           
		 Post post = postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found with postid : " +postid));
		 postRepo.delete(post);
		return "Post deleted successfully with postid : "+postid;
	}
	
	
	@Override
	public PostDto updatePost(long postid, Post updatePost) throws PostException {
		Post existingPost = postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found with postid : "+postid));
        Post post = updateExistingPost(existingPost, updatePost);
        post.setCreateAt(LocalDateTime.now());
        Post savedPost = postRepo.saveAndFlush(post);
        PostDto postDto = mapper.map(savedPost, PostDto.class);
        return postDto;
	}
	

	
	@Override
	public PostDto likedByUser(long userid, long postid) throws PostException, UserException {
		User user = userRepo.findById(userid).orElseThrow(()-> new UserException("User not found with userId : "+userid));
		Post post =postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found With postid : "+postid));
		UserDto userDto = mapper.map(user,UserDto.class);
		post.getLikedByUsers().add(userDto);
		Post savedPost = postRepo.saveAndFlush(post);
		 PostDto postDto = mapper.map(savedPost, PostDto.class);
		return postDto;
	}
	
	
	@Override
	public PostDto unLikedPostByUser(long userid, long postid) throws PostException, UserException {
		
		Set<UserDto> likedUsersOfPost = postRepo.getLikedUsersOfPost(postid);
		
		User user = userRepo.findById(userid).orElseThrow(()->new UserException("user not found with userid:" +userid));
		Post post = postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found with postid : " +postid));
		UserDto userDto = mapper.map(user,UserDto.class);
	     likedUsersOfPost.remove(userDto);
	     
	     post.setLikedByUsers(likedUsersOfPost);
	     
	     postRepo.saveAndFlush(post);
	     
	     PostDto postDto = mapper.map(post, PostDto.class);
		
		return postDto;
	}
	
	
	@Override
	public List<PostDto> getPostOfOtherUser(long userid, long postid) throws PostException, UserException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Post updateExistingPost(Post existingPost ,Post updatePost)
	{
		if(updatePost.getCaption()!=null)
		{
			existingPost.setCaption(updatePost.getCaption());
		}
		if(updatePost.getLocation()!=null)
		{
			existingPost.setLocation(updatePost.getLocation());
		}
		if(updatePost.getImage()!=null)
		{
			existingPost.setImage(updatePost.getImage());
		}
		
		return existingPost;
	}

	
}
