package com.ERP.backend.Error;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiError> handlerUsernameNotFoundException(UsernameNotFoundException ex){
		ApiError apiError  = new ApiError("Username not found with username : " + ex.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
	    ApiError apiError = new ApiError(
	        "Invalid input: " + ex.getMessage(),
	        HttpStatus.BAD_REQUEST
	    );
	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
	    ApiError apiError = new ApiError(
	        ex.getMessage(),
	        HttpStatus.BAD_REQUEST
	    );
	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
	public ResponseEntity<ApiError> handleEntityNotFoundException(Exception ex) {
	    ApiError apiError = new ApiError(
	        "Resource not found: " + ex.getMessage(),
	        HttpStatus.NOT_FOUND
	    );
	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
	    
	    String errorMsg = ex.getBindingResult()
	                        .getFieldErrors()
	                        .stream()
	                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                        .findFirst()
	                        .orElse("Validation error");

	    ApiError apiError = new ApiError(errorMsg, HttpStatus.BAD_REQUEST);

	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
	    ApiError apiError = new ApiError(
	        "You are not authorized to access this resource",
	        HttpStatus.FORBIDDEN
	    );
	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGenericException(Exception ex) {
	    ApiError apiError = new ApiError(
	        "Something went wrong: " + ex.getMessage(),
	        HttpStatus.INTERNAL_SERVER_ERROR
	    );
	    return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
}
