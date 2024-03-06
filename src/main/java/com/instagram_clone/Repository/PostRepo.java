package com.instagram_clone.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.model.Post;

public interface PostRepo extends JpaRepository<Post,Long>{
	
	Optional<Post>findByPostId(long postid);
	
	Optional<Post>findByLocation(String location);
	
	@Query("select p.likedByUsers from Post p where p.postId=:postid")
	public Set<UserDto>getLikedUsersOfPost(long postid);
	

}
