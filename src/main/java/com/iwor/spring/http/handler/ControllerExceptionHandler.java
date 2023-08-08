package com.iwor.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public String handleExceptions(Exception e,
//                                   Model model) {
//        log.error("Failed to return response", e);
//        model.addAttribute("error", e.getMessage());
//        return "error/error";
//    }
}
