package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PersonalInfo;

import java.util.List;

public interface UserJdbcRepository {

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    int[] updateCompanyAndRole(List<User> users);

    int[] updateCompanyAndRoleNamed(List<User> users);
}
