package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PersonalInfo2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        UserJdbcRepository
//        , QuerydslPredicateExecutor<User>
{

    @Query(value = "SELECT firstname, lastname, birth_date birthDate " +
            "FROM spring.users WHERE company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfo2> findAllByCompanyId(Integer companyId);

    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

    @EntityGraph(attributePaths = "company")
    @Query(value = "select u from User u",
            countQuery = "select count(distinct(u.firstname)) from User u")
    Page<User> findAllBy(Pageable pageable);

    List<User> findBy(Sort sort);

    List<User> findTop3ByBirthDateBeforeOrderByIdDesc(LocalDate birthDate);

    @Query("select u from User u " +
            "where lower(u.firstname) like lower(concat('%', :firstname, '%')) " +
            "and lower(u.lastname) like lower(concat('%', :lastname, '%'))")
    List<User> findAllBy(String firstname, String lastname);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.role = :role where u.id in :ids")
    int updateRole(Role role, Long... ids);
}
