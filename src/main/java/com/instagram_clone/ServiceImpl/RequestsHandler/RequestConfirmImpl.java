package com.instagram_clone.ServiceImpl.RequestsHandler;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Repository.RequestsRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Requests;
import com.instagram_clone.model.User;
import com.instagram_clone.service.RequestConfirm;

@Service
public class RequestConfirmImpl implements RequestConfirm{
	
	@Autowired
	private FollowRequestImpl followRequestImpl;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RequestsRepo requestsRepo;
	

	//@Override
	public ResponseUserDto RequestConfirmed( long followUserId ,long requestUserId) throws UserException ,RequestHandler{
		
		User requestedUser = userRepo.findById(requestUserId).orElseThrow(()->new UserException("User not found with requestedId :" +requestUserId));		
		User  followUser = userRepo.findById(followUserId).orElseThrow(()->new UserException("User not found with followUserId :" +followUserId));
		ResponseUserDto requestResponseUserDto = mapper.map(followUser, ResponseUserDto.class);
		ResponseUserDto followResponseUserDto = mapper.map(followUser, ResponseUserDto.class);
		Requests request =new Requests();
		
		User confirmUser =new User();
	    request = followRequestImpl.getRequest(requestUserId,followUserId);
	    if(request ==null)
		{
			throw new RequestHandler("Request not found with requesstUserId: " + requestUserId+ "and followUserid: "+followUserId);
		}
		
		 
		 if(followUser.getAccount().equalsIgnoreCase("private"))
		 {
			 if(request.getRequestStatus().equalsIgnoreCase("requested"))
			 {
				 followRequestImpl.comfirmRequestByPrivateFollowuser(requestedUser ,followUser);
				 
				 request.setRequestStatus("followback");
				 
				 Requests savedRequest = requestsRepo.saveAndFlush(request);
//			 followUser.getRequestedUsers().add(savedRequest);
//			  User user  = userRepo.saveAndFlush(followUser);
				 User user = userRepo.findById(followUserId).get();
				 requestedUser.getRequestedUsers().remove(request);
				 userRepo.saveAndFlush(requestedUser);
				ResponseUserDto savedFollowUser = mapper.map(user,ResponseUserDto.class);
				 
				
				  return savedFollowUser ;
				  
			 }
		 }
		 if(requestedUser.getAccount().equalsIgnoreCase("private"))
		 {
			 if(request.getRequestStatus().equalsIgnoreCase("followback"))
			 {
				 followRequestImpl.confirmRequestByPrivateRequestUser(followUser, requestedUser);
				 requestsRepo.delete(request);
				followUser.getRequestedUsers().remove(followResponseUserDto);
			     confirmUser = userRepo.saveAndFlush(followUser);
			 }
		 }
		 
		 ResponseUserDto returnUser = mapper.map(confirmUser, ResponseUserDto.class);
		return returnUser;
	}

}
