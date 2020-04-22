package com.radecathe.onlinestoreapi.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

@Component("restAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException, org.springframework.security.core.AuthenticationException {
    	
    	CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    	customErrorResponse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").format(new Date().getTime()));
    	customErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    	customErrorResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    	customErrorResponse.setMessage(authException.getMessage());
        
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        OutputStream outputStream = response.getOutputStream();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, customErrorResponse); 
        
        outputStream.flush();
	}
}