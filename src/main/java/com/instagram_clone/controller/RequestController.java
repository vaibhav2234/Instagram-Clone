package com.instagram_clone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram_clone.ExceptionHandler.RequestHandler;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.ServiceImpl.RequestsHandler.FollowRequestImpl;
import com.instagram_clone.ServiceImpl.RequestsHandler.RequestCancelImpl;
import com.instagram_clone.ServiceImpl.RequestsHandler.RequestConfirmImpl;
import com.instagram_clone.ServiceImpl.RequestsHandler.RequestImpl;
import com.instagram_clone.model.Requests;

@RestController
public class RequestController {
	@Autowired
	private RequestImpl requestImpl;
	
	@Autowired
	private RequestConfirmImpl confirmImpl;
	
	@Autowired
	private FollowRequestImpl followRequestImpl;
	@Autowired
	private RequestCancelImpl cancelImpl;
	
	Logger logger =LoggerFactory.getLogger(RequestController.class);	
	@PutMapping("/request/requestedUser/{requestUserId}/followUser/{followUserId}")
	public ResponseEntity<ResponseUserDto> request(@PathVariable long requestUserId ,@PathVariable long followUserId ) throws UserException, RequestHandler
	{
		logger.info("Enter in the RequestController ");
		return ResponseEntity.ok()
				.body(requestImpl.RequestedUser(requestUserId, followUserId));
	}

	@PutMapping("/confirm-request/followUser/{followUserId}/requestedUser/{requestUserId}")
	public ResponseEntity<ResponseUserDto>confirmRequest(@PathVariable long followUserId,@PathVariable long requestUserId ) 
			throws UserException, RequestHandler
	{
		return ResponseEntity.ok()
				.body(confirmImpl.RequestConfirmed(followUserId, requestUserId));
	}
	
	@PutMapping("/cancel-request/requestedUser/{requestUserId}/followUser/{followUserId}")
	public ResponseEntity<String>cancelRequest(@PathVariable long requestUserId ,@PathVariable long followUserId) 
			throws UserException, RequestHandler
	{
		return ResponseEntity.ok()
				.body(cancelImpl.RequestCanceled(requestUserId, followUserId));
	}
	
	@GetMapping("/request/requestedUser/{requestUserId}/followUser/{followUserId}")
	public ResponseEntity<Requests >getRequest(@PathVariable long requestUserId ,@PathVariable long followUserId) throws RequestHandler	
	{
		return new ResponseEntity<Requests>(followRequestImpl.getRequest(requestUserId, followUserId),HttpStatus.OK);
	}
	
	@GetMapping("/request/{reqID}")
	public ResponseEntity<Requests >getRequest(@PathVariable long reqID) throws RequestHandler	
	{
		return new ResponseEntity<Requests>(followRequestImpl.getRequestid(reqID),HttpStatus.OK);
	}
	
	@GetMapping("/requests/{reqID}")
	public ResponseEntity<List<Requests> >getRequestsByReqUserid(@PathVariable long reqID) throws RequestHandler	
	{
		return new ResponseEntity<List<Requests>>(followRequestImpl.getRequestByReqUserId(reqID),HttpStatus.OK);
	}
	
	
}
