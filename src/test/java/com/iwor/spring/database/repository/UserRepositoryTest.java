package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PersonalInfo;
import com.iwor.spring.dto.PersonalInfo2;
import com.iwor.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
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
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        var users2 = userRepository.findAllByCompanyId(1, PersonalInfo2.class);

        assertThat(users).hasSize(2);
        assertThat(users2).hasSize(2);
    }

    @Test
    void checkPageable() {
        var pageRequest = PageRequest.of(1, 2, Sort.sort(User.class).by(User::getId));

        var slice = userRepository.findAllBy(pageRequest);
        var users = slice.getContent();

        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getId()).isEqualTo(3);
        assertThat(users.get(1).getId()).isEqualTo(4);
        users.forEach(user -> System.out.println(user.getCompany().getName()));

        if (slice.hasNext()) {
            var nextSlice = userRepository.findAllBy(slice.nextPageable());
            var nextUsers = nextSlice.getContent();
            assertThat(nextUsers).isNotEmpty();
            assertThat(nextUsers).hasSize(1);
            assertThat(nextUsers.get(0).getId()).isEqualTo(5);
            nextUsers.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

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