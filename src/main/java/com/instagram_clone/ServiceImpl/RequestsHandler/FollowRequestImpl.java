package com.instagram_clone.ServiceImpl.RequestsHandler;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;
import com.instagram_clone.Repository.RequestsRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Requests;
import com.instagram_clone.model.User;
import com.instagram_clone.service.FollowRequest;



@Service
public class FollowRequestImpl implements FollowRequest{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RequestsRepo repo;
	@Autowired
	private ModelMapper mapper;
	
	Logger logger = LoggerFactory.getLogger(FollowRequestImpl.class);
	
	
	//++++++++++++++++++++++++++++If the RequestUser and FollowUser both have the public accounts++++++++++++++++++++++++++
	
	@Override
	public ResponseUserDto sendRequestToPublicFollowUser(User requestedUser,User followUser) throws UserException {
	
		UserDto followUserDto = mapper.map(followUser,UserDto.class);
		UserDto requestUserDto = mapper.map(requestedUser,  UserDto.class);
		requestedUser.getFollowings().add(followUserDto);
	    followUser.getFollowers().add(requestUserDto);
	    User savedRequestUser = userRepo.saveAndFlush(requestedUser);
	    userRepo.saveAndFlush(followUser);
	    
	    ResponseUserDto responseUserDto = mapper.map(savedRequestUser,ResponseUserDto.class);
		return responseUserDto;
	}

	@Override
	public ResponseUserDto followbackRequestToPublicRequestUser(User requestedUser,User followUser) throws UserException
	{
		UserDto followUserDto = mapper.map(followUser,UserDto.class);
		UserDto requestUserDto = mapper.map(requestedUser,  UserDto.class);
		requestedUser.getFollowers().add(followUserDto);
	    followUser.getFollowings().add(requestUserDto);
	    User savedRequestUser = userRepo.saveAndFlush(requestedUser);
	    userRepo.saveAndFlush(followUser);
	    
	    ResponseUserDto responseUserDto = mapper.map(savedRequestUser,ResponseUserDto.class);
		return responseUserDto;
		
		
	}

	//++++++++++++++++++++++++If RequestUser and FollowUser both have the private accounts +++++++++++++++++++++++++++++++++++++++
	
	@Override
	public ResponseUserDto confirmRequestByPrivateRequestUser(User followUser, User requestedUser)
			throws UserException {
		UserDto followUserDto = mapper.map(followUser,UserDto.class);
		UserDto requestUserDto = mapper.map(requestedUser,  UserDto.class);
		requestedUser.getFollowers().add(followUserDto);
	    followUser.getFollowings().add(requestUserDto);
	    User savedRequestUser = userRepo.saveAndFlush(requestedUser);
	    userRepo.saveAndFlush(followUser);
	    
	    ResponseUserDto responseUserDto = mapper.map(savedRequestUser,ResponseUserDto.class);
		return responseUserDto;
	}

	//+++++++++++++++++++++++++++If RequestUser have Public account and FollowUser have Private account++++++++++++++++++++++++++++
	@Override
	public ResponseUserDto comfirmRequestByPrivateFollowuser( User requestedUser ,User followUser)throws UserException {
		UserDto followUserDto = mapper.map(followUser,UserDto.class);
		UserDto requestUserDto = mapper.map(requestedUser,  UserDto.class);
		requestedUser.getFollowings().add(followUserDto);
	    followUser.getFollowers().add(requestUserDto);
	    User savedRequestUser = userRepo.saveAndFlush(requestedUser);
	    userRepo.saveAndFlush(followUser);
	    
	    ResponseUserDto responseUserDto = mapper.map(savedRequestUser,ResponseUserDto.class);
		return responseUserDto;
	
	}

	
	

	//+++++++++++++++++++++++If RequestUser have Private account and FollowUser have Public account+++++++++++++++++++++++++++++++
	@Override
	public ResponseUserDto privateRequestuserSendRequestToPublicFollowuser(User requestedUser,User followUser)throws UserException {
		
		UserDto followUserDto = mapper.map(followUser,UserDto.class);
		UserDto requestUserDto = mapper.map(requestedUser,  UserDto.class);
		
		requestedUser.getFollowings().add(followUserDto);
		followUser.getFollowers().add(requestUserDto);
		 
		userRepo.saveAndFlush(requestedUser);
		userRepo.saveAndFlush(followUser);
		
		return null;
	}
	
	
	public Requests getRequest(long reqId ,Long folloId) throws RequestHandler
	{
		logger.info("enter in the getequest method " +reqId + "  " + folloId);
		
		Optional<Requests> request = repo.findByRequestUserIdAndFollowUserId(reqId, folloId);
		
		if(request.isPresent())
		{
	         Requests requests = request.get();
	         return requests;
		}
		
		
		Optional<Requests> request1 = repo.findByFollowUserIdAndRequestUserId(reqId,folloId);
		if(request1.isPresent())
		{
			Requests requests = request1.get();
			return requests;
		}
		
		
	
		return null;
	}
	public Requests getRequestid(long reqId) throws RequestHandler
	{
		logger.info("enter in the getequest method");
		Requests requests = repo.findByRequestid(reqId).orElseThrow(()->new RequestHandler("NOT FOUND REQUEST !!"));
		logger.info("Request id : " +requests.getRequestid());
		return requests;
	}

	public List<Requests> getRequestByReqUserId(long reqId) throws RequestHandler
	{
		logger.info("enter in the getequest method");
		List<Requests> list = repo.getRequestByRequestUserId(reqId).orElseThrow(()->new RequestHandler("NOT FOUND REQUEST !!"));
		//logger.info("Request id : " +requests.getRequestid());
		return list ;
	}
}
