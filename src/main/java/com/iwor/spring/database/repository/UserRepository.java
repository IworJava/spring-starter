package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where lower(u.firstname) like lower(concat('%', :firstname, '%')) " +
            "and lower(u.lastname) like lower(concat('%', :lastname, '%'))")
    List<User> findAllBy(String firstname, String lastname);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.role = :role where u.id in :ids")
    int updateRole(Role role, Long... ids);
}
