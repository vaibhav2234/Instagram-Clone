package com.instagram_clone.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long >{
	
	public Optional<User> findById(long id);
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
	
	@Query("Select u.followers from User u")
	public List<UserDto> getAllFollowers();
	
	@Query("select u.followings from User u")
	public List<UserDto> getAllFollowings();
	
	@Query("Select DISTINCT u from User u where u.username=:key or u.email=:key or u.mobile=:key")
	public Optional<User> getUserByUsernameEmailMobile(@Param("key") String username);
	
	@Query(value="Select u from User u where u.username like :key or u.fullname like :key")
	public List<User> getSearchUser(@Param("key") String username);

	@Query("Select DISTINCT u.savedPost from User u where u.id=:id")
	public List<Post> getAllSavedPosts(@Param("id") long id);
	
	@Query("Select u from User u where u.id in :users")
	public List<User>getUserBtIds(@Param("users") List<Long>ids);
	
	@Query("Select u from User u where u.email=:email or u.username=:username")
	public Optional<User> getUserByEmailAndUsername( String email , String username);
	
	@Query("select u.roles from User u where u.id= :id")
	public List<Role> getAllUsersRoles(long id);

		
}