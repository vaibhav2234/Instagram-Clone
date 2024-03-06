package com.instagram_clone.ServiceImpl.RequestsHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Repository.RequestsRepo;
import com.instagram_clone.model.Requests;
import com.instagram_clone.service.RequestCancel;

@Service
public class RequestCancelImpl implements RequestCancel {

	
	@Autowired
	private RequestsRepo requestsRepo;
	
	@Autowired
	private FollowRequestImpl followRequestImpl;
	
	
	//@Override
	public String RequestCanceled(long requestedUserId, long followUserId) throws UserException,RequestHandler {
		
		Requests request =followRequestImpl.getRequest(requestedUserId, followUserId);
		if(request ==null)
		{
			throw new RequestHandler("Request not found with requesstUserId: " + requestedUserId+ "and followUserid: "+followUserId);
		}

		if(request.getRequestStatus().equalsIgnoreCase("requested"))
		{
			request.setRequestStatus("follow");
			Requests savedRequest = requestsRepo.saveAndFlush(request);
			
			return savedRequest.getRequestStatus();
		}

		
		return request.getRequestStatus();
	}

}
