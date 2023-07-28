package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);
}
