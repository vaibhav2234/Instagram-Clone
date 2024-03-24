package com.instagram_clone.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.model.Image;
import com.instagram_clone.model.Post;

public interface PostRepo extends JpaRepository<Post,Long>{
	
	Optional<Post>findByPostId(long postid);
	
	Optional<Post>findByLocation(String location);
	
	@Query("select p.likedByUsers from Post p where p.postId=:postid")
	public Set<UserDto>getLikedUsersOfPost(long postid);
	
	@Query("select p.image from Post p where p.postId=:postid")
	public List<Image>getImagesByPostId(@Param("postid")long postid);
	
	@Query("select p from Post p where p.user.id=:userid")
	public List<Post> getPostsByUserId(@Param("userid")long userid);
	
	@Query("select img from Image img outer JOIN  img.post p where p.user.id=:userid")
	public  List<Image> getImagesOfPostsByUserId(@Param("userid")long userid);

}
