package com.instagram_clone.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.annotations.OnDelete;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ImageDto;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.ServiceImpl.PostServiceImpl;
import com.instagram_clone.model.Image;
import com.instagram_clone.model.ImageResponse;
import com.instagram_clone.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PostController {
	
   @Autowired
   private PostServiceImpl postServiceImpl;
   
   Logger logger =LoggerFactory.getLogger(PostController.class);
   
   @PostMapping("/user/{userid}/post")
   public ResponseEntity<PostDto> createPost(
		   @RequestParam("caption")String caption,
		   @RequestParam("location")String location,
		   @RequestPart("file") MultipartFile[] file,
		   HttpServletRequest request,
		   @PathVariable long userid  ) throws PostException, UserException, IOException ,ServletException
   {
	   Post post =new Post();
	   String token =null;
	   post.setCaption(caption);
	   post.setLocation(location);
	   String header = request.getHeader("Authorization");
	   if(header!=null && header.startsWith("Bearer"))
	   {
		  token = header.substring(7);
		  
		   PostDto createdPost = postServiceImpl.createPost(post,token, file);
		   
		   logger.info("PostDtonfrom CreatePost method @PostController = "+createdPost.toString());
				  
		   return  ResponseEntity.ok()
				   
				   .body(createdPost);
	   }
	   {
		   throw new ServletException("Token is invalid !!");
	   }
	  
   }
   
   @GetMapping("post/{postid}")
   public ResponseEntity<PostDto>getOnePost(@PathVariable long postid) throws PostException
   {
	   return ResponseEntity.ok()
			   .body(postServiceImpl.getPostById(postid));
   }
   
   @GetMapping("/all-posts")
   public ResponseEntity<List<PostDto>>getAllPost()throws PostException{
	   
	   return ResponseEntity.ok()
			   .body(postServiceImpl.getAllPosts());
   }

   @DeleteMapping("/post/{postid}")
   public ResponseEntity<String> deletePost(@PathVariable long postid)throws PostException
   {
	   return ResponseEntity.ok()
			   .body(postServiceImpl.deletePostByUser(postid));
   }
   
   @PutMapping("/post/{postid}")
   public ResponseEntity<PostDto>updatePost(@PathVariable long postid,
		   @RequestParam(name = "file" ,required =  false) MultipartFile file,
		   @RequestParam(name="caption",required = false ) String caption ,
		   @RequestParam (name ="location", required=false ) String location) throws PostException, IOException
   {
	   Post post =new Post();
	   Image image =new Image();
	   Blob blob =null;
	   try
	   {
		   blob =new SerialBlob(file.getBytes());
	   }catch (SerialException e) {
		 String message = e.getMessage();
		 new SerialException(message);
	   }
	   catch(SQLException e)
	   {
		   
		   String message = e.getMessage();
		   new SQLException(message);
	   }
	   image.setImagename(file.getOriginalFilename());
	   image.setImagetype(file.getContentType());
	   image.setImagecontent(blob);
	   List<Image>images =new ArrayList<>();
	   images.add(image);
	   post.setCaption(caption);
	   post.setLocation(location);
	  post.setImage(images);
	   
	   return ResponseEntity.ok()
			   .body(postServiceImpl.updatePost(postid, post));
   }
   
  
    
   @PutMapping("/post/{postid}/like-by-user/{userid}")
   public ResponseEntity<PostDto>postLikedByUser(@PathVariable long userid ,@PathVariable long postid) throws PostException, UserException
   {
	   return ResponseEntity.ok()
			   .body(postServiceImpl.likedByUser(userid, postid));
   }
   
   
   @PutMapping("/post/{postid}/unlike-by-user/{userid}")
   public ResponseEntity<PostDto>unLikePostByUser(@PathVariable long postid ,@PathVariable long userid) throws PostException, UserException
   {
	   return ResponseEntity.ok()
			   .body(postServiceImpl.unLikedPostByUser(userid, postid));
   }
   
   @GetMapping("/post/{id}/images")
   public ResponseEntity<List<String>> getImagesbyPostId(@PathVariable long id) throws SQLException
   {
	   List<String> ImageUrls = postServiceImpl.getImageByPostId(id);

	   return ResponseEntity.ok()
			   .body(ImageUrls);
	  
	   
   }

   @GetMapping("user/{id}/posts/images")
   public ResponseEntity<List<ImageDto>> getImagesPostsByUserId(@PathVariable long id) throws SQLException
   {
	 List<ImageDto> imagesPostsByUSerId = postServiceImpl.getImagesPostsByUserId(id);

	   return ResponseEntity.ok()
			   .body(imagesPostsByUSerId);
	 
	   
   }

   @GetMapping("user/{id}/posts")
   public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable long id) throws SQLException
   {
	 List<PostDto> imagesPostsByUSerId = postServiceImpl.getPostsByUserId(id);

	   return ResponseEntity.ok()
			   .body(imagesPostsByUSerId);
	 
	   
   }
  
   
}
