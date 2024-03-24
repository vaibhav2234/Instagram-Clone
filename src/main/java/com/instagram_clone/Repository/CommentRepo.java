package com.instagram_clone.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram_clone.model.Comments;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Long> {

	@Query("select c from Comments c where c.user.id=:userid and c.post.postId=:postid and c.parentComment.commentId=:parentcommentid")
	Optional<Comments> getCommentByPostidUseridAndParantCommentid(@Param("postid") long postid , @Param("userid") long userid,@Param("parentcommentid") long parentcommentid);
   
	 Optional<Comments>findByCommentId(long commentId); 

}
