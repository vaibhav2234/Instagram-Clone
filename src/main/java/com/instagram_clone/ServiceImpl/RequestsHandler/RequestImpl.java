package com.instagram_clone.ServiceImpl.RequestsHandler;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Repository.RequestsRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Requests;
import com.instagram_clone.model.User;
import com.instagram_clone.service.Request;

@Service
public class RequestImpl implements Request{
	
	@Autowired 
	private UserRepo userRepo;
	
	@Autowired
	private FollowRequestImpl followRequestImpl;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RequestsRepo requestsRepo; 
	
	Logger logger =LoggerFactory.getLogger(RequestImpl.class);
	
	
	@Override
	public ResponseUserDto RequestedUser(long requestUserId, long followUserId) throws UserException ,RequestHandler{
		User requestedUser = userRepo.findById(requestUserId).orElseThrow(()-> new UserException("User not found with requestUserId : "+requestUserId));
		User followUser = userRepo.findById(followUserId).orElseThrow(()-> new UserException(" @RequesstImpl User not found with followUserId : "+followUserId));
		
		 Requests request =null;
		 Requests getReq =followRequestImpl.getRequest(requestUserId,followUserId);
	//	logger.info("RequestId from the getReq = "+getReq.getRequestid());
		logger.info("enter in the requestService");
		if(getReq == null)
		{
			request= new Requests(requestUserId,followUserId ,"requested");
			requestsRepo.saveAndFlush(request);
		}
		else
		{
			request =getReq;
		}
		logger.info("enter in the requestService1");
		
		if( request != null && request.getRequestStatus().equalsIgnoreCase("follow"))
		{
			request.setRequestStatus("requested");
			requestsRepo.saveAndFlush(request);
		}
		
		
		
	
		logger.info("enter in the requestService 2");
		logger.info("enter in the requestService request" + request.getRequestid());
		
		if( followUser.getAccount().equalsIgnoreCase("public")) 
		{
				if(requestedUser.getAccount().equalsIgnoreCase("public") && request.getRequestStatus().equalsIgnoreCase("requested"))
				{
					logger.info("enter in the requestService 3");
					followRequestImpl.sendRequestToPublicFollowUser(requestedUser,followUser);
					 logger.info("enter in the requestService 4");
				   
				    request.setRequestStatus("followback");
				    
				    requestsRepo.saveAndFlush(request);
				    followUser.getRequestedUsers().add(request);
				    User savedFollowUser = userRepo.saveAndFlush(followUser);
				    ResponseUserDto savedFollowResponseUserDto = mapper.map(savedFollowUser, ResponseUserDto.class);
				    
				    logger.info("enter in the requestService 5");
				    return savedFollowResponseUserDto;
					
				}
				else if(requestedUser.getAccount().equalsIgnoreCase("private") && request.getRequestStatus().equalsIgnoreCase("requested"))
				{
					logger.info("enter in the requestService 6");
					followRequestImpl.privateRequestuserSendRequestToPublicFollowuser(requestedUser,followUser);
					logger.info("enter in the requestService 7");
					 	
					Requests request1 = followRequestImpl.getRequest(requestUserId,followUserId);
					if(request1 ==null)
					{
						throw new RequestHandler("Request not found with requesstUserId: " + requestUserId+ "and followUserid: "+followUserId);
					}
					
					logger.info("enter in the requestService 9");
					 request1.setRequestStatus("followback");
					 logger.info("enter in the requestService 10");
					    
					    Requests savedREquest = requestsRepo.saveAndFlush(request1);
					    followUser.getRequestedUsers().add(savedREquest);
					    User savedFollowUser = userRepo.saveAndFlush(followUser);
					    logger.info("enter in the requestService 11");
					    ResponseUserDto savedFollowResponseUserDto = mapper.map(savedFollowUser, ResponseUserDto.class);
					    logger.info("enter in the requestService 12");
					    return savedFollowResponseUserDto;
						
					    
				}
			
		}
		
		
		
	 if(requestedUser.getAccount().equalsIgnoreCase("public") )
		{
			if(followUser.getAccount().equalsIgnoreCase("public") && request.getRequestStatus().equalsIgnoreCase("followback"))
			{
				followRequestImpl.followbackRequestToPublicRequestUser(  followUser ,requestedUser);
				followUser.getRequestedUsers().remove(request);
				 requestsRepo.delete(request);
				    User savedFollowUser = userRepo.saveAndFlush(followUser);
				    ResponseUserDto savedFollowResponseUserDto = mapper.map(savedFollowUser, ResponseUserDto.class);
				    return savedFollowResponseUserDto;
					
			}
			

		}
	 
	 if( requestedUser.getAccount().equalsIgnoreCase("public"))
	 {
		 if(followUser.getAccount().equalsIgnoreCase("private") && request.getRequestStatus().equalsIgnoreCase("followback"))
		 {
			 requestedUser.getRequestedUsers().remove(request);
			 userRepo.saveAndFlush(requestedUser);
			 request.setFollowUserId(requestUserId);
			 request.setRequestUserId(followUserId);
			 request.setRequestStatus("requested");
			 
			 Requests savedRequests = requestsRepo.saveAndFlush(request);
			 
			
			 
			 followUser.getRequestedUsers().add(savedRequests);
			 
			 User user = userRepo.saveAndFlush(followUser);
			 ResponseUserDto responseUserDto = mapper.map(user, ResponseUserDto.class);
			 
			 return responseUserDto;
			 
			 
		 }
	 }
	 
	  
	 logger.info("enter in the requestService 122");
		
		Requests getReq1 = followRequestImpl.getRequest(requestUserId,followUserId);
		
		if(getReq1.getRequestStatus().equalsIgnoreCase("requested"))
		{
			 logger.info("enter in the requestService 122");
		        followUser.getRequestedUsers().add(getReq1);
		        requestedUser.getRequestedUsers().add(getReq1);
		        userRepo.saveAndFlush(followUser);
		        userRepo.save(requestedUser);

		        
		}
		else if(getReq1.getRequestStatus().equalsIgnoreCase("followback"))
		{
			getReq1.setRequestStatus("requested");
		     Requests savedRequest = requestsRepo.saveAndFlush(getReq1);
			requestedUser.getRequestedUsers().add(savedRequest);
			followUser.getRequestedUsers().add(savedRequest);
			
			userRepo.saveAndFlush(requestedUser);
			userRepo.saveAndFlush(followUser);
		}
		
		
		
		return null;
	}

}
