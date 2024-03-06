package com.instagram_clone.ServiceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.instagram_clone.AppConstant.AppConstant;
import com.instagram_clone.ExceptionHandler.PostException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.PostDto;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.Repository.PostRepo;
import com.instagram_clone.Repository.RoleRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Post;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

import com.instagram_clone.service.UserService;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;

@Service
@Validated
public class UserServiceImpl implements UserService{
	
	@Autowired
    private ModelMapper mapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private UserRepo userRepo;
	@Autowired
	public UserServiceImpl(UserRepo userRepo )
	{
		this.userRepo=userRepo;
	}

	Logger logger =LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public ResponseUserDto createUser(User user) throws UserException {
		//Role role =new Role();
		logger.info("Enter in the Create User from UserServiceImpl@UserController");
		
		 Optional<User> existingEmailWithUser = userRepo.findByEmail(user.getEmail());
		 
		 Optional<User> existingUsernameWithUser=userRepo.findByUsername(user.getUsername());
		 
		 if(existingEmailWithUser.isPresent())
		 {
			 throw new UserException("Email is Already present !!.Please enter another Email");
		 }
		 else if(existingUsernameWithUser.isPresent())
		 {
			 throw new UserException("Username is Already present !! Please enter another Username.");
		 }
		 
		 user.setDate(new Date());
		 user.setPassword(encoder.encode(user.getPassword()));
		 
		Role role= roleRepo.findByRoleid(AppConstant.USER_ROLEID).get();
		  
//		 role.setRoleid(AppConstant.USER_ROLEID);
//		 role.setRolename(AppConstant.ROLE_USER);

		    if (user.getRoles() == null) {
		        user.setRoles(new HashSet<>());
		    }
		 
		 user.getRoles().add(role);
		 
		 User savedUser = userRepo.save(user);
		 ResponseUserDto responseUserDto = mapper.map(savedUser,ResponseUserDto.class);
		 logger.info("User regsitored successfully");
		return  responseUserDto;
	}

	@Override
	public ResponseUserDto getUserById(long id) throws UserException {
		logger.info("Enter in the get User from UserServiceImpl@UserController");
		
		User user = userRepo.findById(id).orElseThrow(() -> new UserException("User not found with ID: " +  id));
		logger.info("User feached successfully from UserServiceImpl@UserController");
//		 Set<UserDto> followers =new HashSet<>(user.getFollowers());
//		 Set<UserDto> followings =new HashSet<>(user.getFollowings());
//		 user.setFollowers(followers);
//		 user.setFollowings(followings);
		ResponseUserDto responseUserDto = mapper.map(user, ResponseUserDto.class);
		return responseUserDto;
	}


	@Override
	public String deleteUser(long id) throws UserException {
		logger.info("delete User function from UserServiceImpl@UserController");
		
		User user = userRepo.findById(id).orElseThrow(()-> new UserException("User not found with ID:" +  id));
		user.getRoles().clear();
		userRepo.delete(user);
		logger.info("user deleted Successfully from UserServiceImpl@UserController");
		
		
		return "User Deleted Successfully with userid ="+id;
	}

	@Override
	@Validated(UserServiceImpl.class)
	public ResponseUserDto updateUser(  User updateUser,long id) throws UserException {
		logger.info("Update User Funcrion from UserServiceImpl@UserController");
		
		User user = userRepo.findById(id).orElseThrow(()-> new UserException("User not found with ID:" +  id));
		
		User user1 =setUpdateFields(user,updateUser);
		//System.out.println(user1.getEmail()+ "  " + user1.getFullName()+ "" );
		User savedUpdatedUser = userRepo.saveAndFlush(user1);
		logger.info("User Updated successfully UserServiceImpl@UserController");
		
		ResponseUserDto userToResponseUserDto = userToResponseUserDto(savedUpdatedUser);
		
		return userToResponseUserDto;
	}

	@Override
	public ResponseUserDto getUserByUsername(String username) throws UserException {
		logger.info(" Get USer by Username function from UserServiceImpl@UserController");
		
		User user = userRepo.findByUsername(username).orElseThrow(()-> new UserException("User not found with Username + " + username));
		logger.info("User feached by is username from UserServiceImpl@UserController");
		ResponseUserDto responseUserDto = userToResponseUserDto(user);
		return responseUserDto;
	}

	@Override
	public List<UserDto> followers() throws UserException {
		logger.info("Get All Followers function from UserServiceImpl@UserController");
		
		List<UserDto> allFollowers = userRepo.getAllFollowers();
		
		logger.info("feaching followers successfully from UserServiceImpl@UserController");
		
		
		return allFollowers;
	}

	@Override
	public List<UserDto> folllowings() throws UserException {
		logger.info("Get all Followings function from UserServiceImpl@UserController");
		
	     List<UserDto> allFollowings = userRepo.getAllFollowings();
	     
	     logger.info(" feaching all followings successfully from UserServiceImpl@UserController");
			
		return allFollowings;
	}

	@Override
	public String followUser(long reqUserId, long followUserId) throws UserException {
		User followedUser = userRepo.findById(followUserId).orElseThrow(()->new UserException("User not found with userid : "+followUserId));
		User requestedUser = userRepo.findById(followUserId).orElseThrow(()->new UserException("User not found with userid : "+reqUserId));
		 UserDto userDto = mapper.map(followedUser,UserDto.class);
		 
		 requestedUser.getFollowers().add(userDto);
		 
		 userRepo.saveAndFlush(requestedUser);
		 
		 
		 return null;
	}

	@Override
	public String unFollowUser(long reqUserId, long FollowUserId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResponseUserDto> getAllUser() throws UserException {
		logger.info("Get All Users function UserServiceImpl@UserController");
		 List<User> allUsers = userRepo.findAll();
		 
		 List<ResponseUserDto>users =allUsers.stream().map((user)->userToResponseUserDto(user)).collect(Collectors.toList());
		 logger.info("feaching all users successfully from UserServiceImpl@UserController");
			
		return users;
	}

	@Override
	public List<ResponseUserDto> searchUsers(String query) throws UserException {
		logger.info("Get All Searched Users function UserServiceImpl@UserController");
		List<User> searchUser = userRepo.getSearchUser("%"+query+"%");
		List<ResponseUserDto>dto=searchUser.stream().map((user)->this.mapper.map(user, ResponseUserDto.class)).collect(Collectors.toList());
		
		logger.info("Get All Searched Users successfully  UserServiceImpl@UserController");
		return dto;
	}
	
	public  User setUpdateFields(User user ,User updatedUser) throws UserException
	{
		if(updatedUser.getFullname()!=null)
		{
			user.setFullname(updatedUser.getFullname());
		}
	
		if(updatedUser.getGender()!=null)
		{
			user.setGender(updatedUser.getGender());
		}
		if(updatedUser.getUsername()!=null)
		{
			Optional<User> existedUSer = userRepo.findByUsername(updatedUser.getUsername());
			
			if(existedUSer.isPresent())
			{
				throw new UserException("Username already present.Enter another username.");
			}
	
			user.setUsername(updatedUser.getUsername());
		}
		
		if(updatedUser.getBio()!=null)
		{
			user.setBio(updatedUser.getBio());
		}
		if(updatedUser.getImage()!=null)
		{
			user.setImage(updatedUser.getImage());
		}
		if(updatedUser.getWebsite()!=null)
		{
			user.setWebsite(updatedUser.getWebsite());
		}
		
		
		
		return user;
	}

	@Override
	public List<Role> getAllUsersRoles(long id) throws UserException {
		List<Role> allUsersRoles = userRepo.getAllUsersRoles(id);
		if(allUsersRoles.size()==0)
		{
			throw new UserException("User not exist with id : "+id);
		}
		return allUsersRoles;
	}

	@Override
	public List<PostDto> getAllSavedPostByUser(long id) throws UserException {
		List<Post> savedPosts = userRepo.getAllSavedPosts(id);
		if(savedPosts.size()==0)
		{
			throw new UserException("No saved Posts "); 
		}
		List<PostDto>posts = savedPosts.stream().map((post)-> mapper.map(post,PostDto.class)).collect(Collectors.toList());
		return posts;
	}
	 
	//<<<<<<<---------------------------User-To-ResponseUserDto-Converter------------------------------------->>>>>>>>
	public ResponseUserDto userToResponseUserDto(User user)
	{
		ResponseUserDto responseUserDto = mapper.map(user,ResponseUserDto.class);
		return responseUserDto;
	}
	
	//<<<<<<<---------------------------ResponseUserDto-To-User-Converter------------------------------------->>>>>>>>
	public User responseUserDtoToUser(ResponseUserDto responseUserDto)
	{
		User user = mapper.map(responseUserDto,User.class);
		return user;
	}

	@Override
	public ResponseUserDto getUserByUsernameEmailMobile(String username) throws UserException{
		
		Optional<User> user1 = userRepo.getUserByUsernameEmailMobile(username);
		if(user1.isEmpty())
		{
			throw new UserException("User not Found with Username : " +username);
		}
		
		User user =user1.get();
		ResponseUserDto userToResponseUserDto = userToResponseUserDto(user);
		return userToResponseUserDto;
	}

	@Override
	public ResponseUserDto savePostbyUser(long userid, long postid) throws UserException, PostException {
		User user = userRepo.findById(userid).orElseThrow(()->new UserException("User not found with userid: "+userid));
		Post post = postRepo.findByPostId(postid).orElseThrow(()->new PostException("Post not found with postid :"+postid));
		
		user.getSavedPost().add(post);
		User savedUser = userRepo.saveAndFlush(user);
		 List<Post>posts =savedUser.getPosts();
		
		List<PostDto> posts1 =posts.stream().map((post2)->mapper.map(post2,PostDto.class)).collect(Collectors.toList());
		
		ResponseUserDto savedUserDto  = userToResponseUserDto(savedUser);
		savedUserDto.setPosts(posts1);
		return savedUserDto;
	}

	@Override
	public String unSavedPostByUser(long userid, long postid) throws UserException, PostException {
		Post post = postRepo.findByPostId(postid).orElseThrow(()-> new PostException("Post Not Found With Postid : " +postid));
		
		User user = userRepo.findById(userid).orElseThrow(()->new UserException("User not found with userid :" +userid));
		
		List<Post> posts = userRepo.getAllSavedPosts(userid);
		posts.remove(post);
	
		user.setSavedPost(posts);
		
		userRepo.saveAndFlush(user);
		
		return "Saved post removed successfully";
	}
}
