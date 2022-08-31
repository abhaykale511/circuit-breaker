package com.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> exception(Throwable e){
		Map<String, Object> map=new HashMap<>();
		map.put("Message", e.getMessage());
		return new ResponseEntity<Object>(map,HttpStatus.OK);
		
	}

}
