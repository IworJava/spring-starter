package com.iwor.spring.integration.http.controller;

import com.iwor.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.iwor.spring.dto.UserCreatEditDto.Fields.birthDate;
import static com.iwor.spring.dto.UserCreatEditDto.Fields.companyId;
import static com.iwor.spring.dto.UserCreatEditDto.Fields.firstname;
import static com.iwor.spring.dto.UserCreatEditDto.Fields.lastname;
import static com.iwor.spring.dto.UserCreatEditDto.Fields.role;
import static com.iwor.spring.dto.UserCreatEditDto.Fields.username;
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

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", IsCollectionWithSize.hasSize(5)));
    }

    @Test
    void findById() {
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test@gmail.com")
                        .param(firstname, "test")
                        .param(lastname, "testTest")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                        .param(birthDate, "01-01-2000")
                )
                .andExpectAll(
                        status().is(302),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}