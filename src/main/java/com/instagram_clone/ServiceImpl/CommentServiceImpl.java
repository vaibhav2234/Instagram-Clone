package com.instagram_clone.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.CommentException;
import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.CommentDto;
import com.instagram_clone.Playloads.PostCommentDto;
import com.instagram_clone.Repository.CommentRepo;
import com.instagram_clone.Repository.PostRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Comments;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.User;
import com.instagram_clone.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public PostCommentDto createComment(CommentDto commentDto, long userid, long postid,long parentcommentid)throws UserException ,PostException,CommentException {
		Comments comment = mapper.map(commentDto, Comments.class);
		
		Post post = postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found with postid :"+postid));	
		User user = userRepo.findById(userid).orElseThrow(()-> new UserException("User not ffound with userid : "+userid));
		comment.setPost(post);
		comment.setUser(user);
		
		//Comments savedComment = commentRepo.save(comment);
		 comment.setCreatedAt(LocalDateTime.now());
		if(parentcommentid==0)
		{
			comment.setParentComment(comment);
		}
		else
		{
			Optional<Comments> comment1 = commentRepo.findByCommentId(parentcommentid);
			if(comment1.isEmpty())
			{
				throw new CommentException("comment not found with id commentid :" +parentcommentid);
			}
          	 Comments parentComment =comment1.get();
          	 parentComment.getReplies().add(comment);
          	 comment.setParentComment(parentComment);
		}
		
		Comments savedComment = commentRepo.save(comment);
		PostCommentDto commentDto2 = mapper.map(savedComment,PostCommentDto.class);
		
		return commentDto2;
	}
	
	public PostCommentDto getCommentByCommentid(long id) throws CommentException
	{
		Optional<Comments> comment = commentRepo.findByCommentId(id);
		if(comment.isEmpty())
		{
			throw new CommentException("comment not found with id commentid :" +id);
		}
		
		Comments comment1=comment.get();
		PostCommentDto commentDto = mapper.map(comment1, PostCommentDto.class);
		return commentDto;
		
		
	}
	
	public String deleteComment(long postid,long userid,long parentommentid,long commentid)
	{
		
		commentRepo.deleteById(commentid);
		
		return "Comment deleted successfullly";
	}
	
	

}
