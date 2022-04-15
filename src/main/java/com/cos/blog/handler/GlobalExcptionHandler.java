package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;


@ControllerAdvice	// 어디서든 exception이 발생했을 때 여기 내부 함수를 실행
@RestController
public class GlobalExcptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class) // IllegalArgumentException이 발생하면 함수에게 전달
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
}
