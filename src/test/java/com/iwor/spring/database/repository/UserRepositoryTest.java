package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@RequiredArgsConstructor
@IT
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkUpdate() {
        var user = userRepository.getReferenceById(1L);
        assertSame(Role.ADMIN, user.getRole());
        assertEquals(Role.ADMIN, user.getRole());

        var i = userRepository.updateRole(Role.USER, 1L, 5L);

        var sameUser = userRepository.getReferenceById(1L);
        assertAll(
                () -> assertSame(Role.USER, sameUser.getRole()),
                () -> assertEquals(Role.USER, sameUser.getRole())
        );
    }

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("i", "i");
    }


}