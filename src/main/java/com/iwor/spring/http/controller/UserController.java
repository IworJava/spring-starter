package com.iwor.spring.http.controller;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.dto.UserCreatEditDto;
import com.iwor.spring.dto.UserFilter;
import com.iwor.spring.service.CompanyService;
import com.iwor.spring.service.UserService;
import com.iwor.spring.validation.group.Creation;
import com.iwor.spring.validation.group.Update;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model,
                          @ModelAttribute("filter") UserFilter filter,
                          RedirectAttributes redirectAttributes,
                          Pageable pageable) {
        var pageResponse = userService.findAll(filter, pageable);
        model.addAttribute("users", pageResponse);
        redirectAttributes.addFlashAttribute("filter", filter);
        return "user/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
//    @PreAuthorize("hasAuthority(T(com.iwor.spring.database.entity.Role).ADMIN.getAuthority())")
    @GetMapping("/{id}")
    public String findById(Model model,
                           @PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//                .orElse("redirect:/users");
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreatEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }

    @PostMapping
    public String create(@Validated({Default.class, Creation.class}) UserCreatEditDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", dto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
            return "redirect:users/registration";
        }
        userService.create(dto);
        return "redirect:/login";
    }

    // т.к. в http нет PUT
//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Validated({Default.class, Update.class}) UserCreatEditDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
            return "redirect:/users/" + id;
        }
        return userService.update(id, dto)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // т.к. в http нет DELETE
//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
