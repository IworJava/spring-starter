package com.iwor.spring.integration.service;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.dto.UserCreatEditDto;
import com.iwor.spring.integration.IntegrationTestBase;
import com.iwor.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1_ID = 1L;
    private static final String USER_1_USERNAME = "ivan@gmail.com";
    private static final Long USER_NEW_ID = 6L;
    private static final Integer COMPANY_1_ID = 1;
    private static final String COMPANY_1_NAME = "Google";

    private final UserService userService;

    @Test
    void findAll() {
        int expected = 5;
        var actual = userService.findAll();
        assertThat(actual).hasSize(expected);
    }

    @Test
    void findById() {
        var actual = userService.findById(USER_1_ID);

        assertThat(actual).isPresent();
        assertThat(actual.get().getUsername()).isEqualTo(USER_1_USERNAME);
    }

    @Test
    void create() {
        UserCreatEditDto dto = new UserCreatEditDto(
                "test@gmail.com",
                "first",
                "last",
                LocalDate.now(),
                null,
                Role.ADMIN,
                COMPANY_1_ID
        );

        var actual = userService.create(dto);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(USER_NEW_ID),
                () -> assertThat(actual.getUsername()).isEqualTo(dto.getUsername()),
                () -> assertThat(actual.getFirstname()).isEqualTo(dto.getFirstname()),
                () -> assertThat(actual.getLastname()).isEqualTo(dto.getLastname()),
                () -> assertThat(actual.getBirthDate()).isEqualTo(dto.getBirthDate()),
                () -> assertThat(actual.getRole()).isSameAs(dto.getRole()),
                () -> assertThat(actual.getCompany().id()).isEqualTo(COMPANY_1_ID),
                () -> assertThat(actual.getCompany().name()).isEqualTo(COMPANY_1_NAME)
        );
    }

    @Test
    void update() {
        UserCreatEditDto dto = new UserCreatEditDto(
                "test@gmail.com",
                "first",
                "last",
                LocalDate.now(),
                null,
                Role.USER,
                COMPANY_1_ID
        );

        var actual = userService.update(USER_1_ID, dto).get();

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(USER_1_ID),
                () -> assertThat(actual.getUsername()).isEqualTo(dto.getUsername()),
                () -> assertThat(actual.getFirstname()).isEqualTo(dto.getFirstname()),
                () -> assertThat(actual.getLastname()).isEqualTo(dto.getLastname()),
                () -> assertThat(actual.getBirthDate()).isEqualTo(dto.getBirthDate()),
                () -> assertThat(actual.getRole()).isSameAs(dto.getRole()),
                () -> assertThat(actual.getCompany().id()).isEqualTo(COMPANY_1_ID),
                () -> assertThat(actual.getCompany().name()).isEqualTo(COMPANY_1_NAME)
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertFalse(userService.delete(-1L)),
                () -> assertTrue(userService.delete(USER_1_ID))
        );
    }
}
