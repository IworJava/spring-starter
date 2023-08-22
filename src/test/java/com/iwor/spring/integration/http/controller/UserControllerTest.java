package com.iwor.spring.integration.http.controller;

import com.iwor.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.iwor.spring.dto.UserCreateEditDto.Fields.birthDate;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.companyId;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.firstname;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.lastname;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.rawPassword;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.role;
import static com.iwor.spring.dto.UserCreateEditDto.Fields.username;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
//        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
//        User testUser = new User("test@gmail.com", "test", roles);
//        var authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
//
//        var context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authenticationToken);
//        SecurityContextHolder.setContext(context);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
//                        .with(user("test@gmail.com")
//                                .password("test")
//                                .authorities(Role.ADMIN, Role.USER))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void findById() {
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .with(anonymous())
                        .with(csrf())
                        .param(username, "test@gmail.com")
                        .param(rawPassword, "test")
                        .param(firstname, "test")
                        .param(lastname, "testTest")
                        .param(birthDate, "01-01-2000")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                )
                .andExpectAll(
                        status().is(302),
                        redirectedUrlPattern("/login*")
                );
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}