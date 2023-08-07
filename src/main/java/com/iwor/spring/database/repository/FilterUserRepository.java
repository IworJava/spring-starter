package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);
    PageResponse<User> findAllByFilter(UserFilter userFilter, Pageable pageable);
}
