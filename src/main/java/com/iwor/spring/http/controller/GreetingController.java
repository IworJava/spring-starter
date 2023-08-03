package com.iwor.spring.http.controller;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.dto.UserReadDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("api/v1")
@SessionAttributes("user")
public class GreetingController {

    @ModelAttribute(name = "roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(ModelAndView modelAndView,
                        Model model,
                        HttpServletRequest request,
                        HttpServletResponse response) {
//        modelAndView.addObject("user", new UserReadDto(1L, "aabbaa"));
        model.addAttribute("user", new UserReadDto(1L, "aabbaa"));
        return "greeting/hello";
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public ModelAndView hello2(ModelAndView modelAndView,
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
    public ModelAndView bye(ModelAndView modelAndView,
                            @SessionAttribute(required = false) UserReadDto user) {
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }
}
