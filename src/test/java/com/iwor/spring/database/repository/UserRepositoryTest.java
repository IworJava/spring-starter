package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

@RequiredArgsConstructor
@IT
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkSort() {
//        var sort = Sort.by("lastname").descending().and(Sort.by("firstname").descending());
        var sortType = Sort.sort(User.class);
        var sort = sortType.by(User::getLastname).descending()
                .and(sortType.by(User::getFirstname).descending());

        var users = userRepository.findBy(sort);

        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getId()).isEqualTo(4);

    }

    @Test
    void checkOrder() {
        var user = userRepository.findTop3ByBirthDateBeforeOrderByIdDesc(LocalDate.now());

        assertAll(
                () -> assertFalse(user.isEmpty()),
                () -> assertEquals(3, user.size()),
                () -> assertEquals(5, user.get(0).getId()),
                () -> assertEquals(4, user.get(1).getId()),
                () -> assertEquals(3, user.get(2).getId())
        );
    }

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