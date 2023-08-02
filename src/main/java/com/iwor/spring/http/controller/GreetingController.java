package com.iwor.spring.http.controller;

import com.iwor.spring.dto.UserReadDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("api/v1")
public class GreetingController {

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable Integer id,
                              @RequestParam(required = false) Integer age,
                              @RequestHeader String accept,
                              @CookieValue("JSESSIONID") String jsessionId) {
        var age1 = request.getParameter("age");
        var accept1 = request.getHeader("accept");
        var cookies = request.getCookies();

        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    @GetMapping("/bye{id}")
    public ModelAndView bye(ModelAndView modelAndView) {
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }
}
