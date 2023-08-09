package com.iwor.spring.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.iwor.spring.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
