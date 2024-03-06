package com.instagram_clone.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.instagram_clone.model.ErrorDetails;

@ControllerAdvice
public class GlobleExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ex ,WebRequest req)
	{
		String message = ex.getMessage();
		
		ErrorDetails error = new ErrorDetails(message,req.getDescription(false),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception ex ,WebRequest req)
	{
		String message = ex.getMessage();
	
		ErrorDetails error = new ErrorDetails(message,req.getDescription(true),LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String ,String>>methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
	{
		Map<String,String> resp =new HashMap();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String field = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			resp.put(field, defaultMessage);
		});
		
		return new ResponseEntity<Map<String,String>>(resp ,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PostException.class)
	public ResponseEntity<ErrorDetails>postExceptionHandler(PostException ex ,WebRequest req)
	{
		ErrorDetails error =new ErrorDetails(ex.getMessage() ,req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(error ,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoleException.class)
	public ResponseEntity<ErrorDetails>roleExceptionHandler(RoleException ex ,WebRequest req)
	{
		ErrorDetails error =new ErrorDetails(ex.getMessage(),req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(error ,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorDetails>ioExceptionHandler(IOException io,WebRequest req)
	{
		 String message = io.getMessage();
		 
		 ErrorDetails error =new ErrorDetails(message ,req.getDescription(false),LocalDateTime.now());
		 
		 return new ResponseEntity<ErrorDetails>(error ,HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(ImageException.class)
	public ResponseEntity<ErrorDetails>handleImageException(ImageException ex ,WebRequest req)
	{
		ErrorDetails error = new ErrorDetails(ex.getMessage() ,req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<>(error ,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RequestHandler.class)
	public ResponseEntity<ErrorDetails>handleImageException(RequestHandler ex ,WebRequest req)
	{
		ErrorDetails error = new ErrorDetails(ex.getMessage() ,req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<>(error ,HttpStatus.BAD_REQUEST);
	}
}
