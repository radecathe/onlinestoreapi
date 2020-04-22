package com.radecathe.onlinestoreapi.exception;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
    	CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    	customErrorResponse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").format(new Date().getTime()));
    	customErrorResponse.setStatus(httpStatus.value());
    	customErrorResponse.setError(httpStatus.getReasonPhrase());
    	customErrorResponse.setMessage(exception.getMessage());
        
        return new ResponseEntity<>(customErrorResponse, httpHeaders, httpStatus); 
    }
	
	@Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        
        MethodArgumentNotValidErrorResponse methodArgumentNotValidErrorResponse = new MethodArgumentNotValidErrorResponse();
        methodArgumentNotValidErrorResponse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").format(new Date().getTime()));
        methodArgumentNotValidErrorResponse.setStatus(httpStatus.value());
        methodArgumentNotValidErrorResponse.setError(httpStatus.getReasonPhrase());
        methodArgumentNotValidErrorResponse.setMessage(methodArgumentNotValidException.getMessage());
        methodArgumentNotValidErrorResponse.setField(methodArgumentNotValidException.getClass().getName());
        methodArgumentNotValidErrorResponse.setField(fields);
        methodArgumentNotValidErrorResponse.setFieldMessage(fieldMessages);
        
        return new ResponseEntity<>(methodArgumentNotValidErrorResponse, httpStatus);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
    	CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    	customErrorResponse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").format(new Date().getTime()));
    	customErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	customErrorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    	customErrorResponse.setMessage(exception.getMessage());
    	
    	StringWriter stringWriter = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stringWriter);
    	exception.printStackTrace(printWriter);
    	
    	customErrorResponse.setTrace(stringWriter.toString());
        
        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
    	CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    	customErrorResponse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").format(new Date().getTime()));
    	customErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    	customErrorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
    	customErrorResponse.setMessage(resourceNotFoundException.getMessage());
        
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}