package com.iwor.spring.dto;

import com.iwor.spring.database.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
    CompanyReadDto company;
}
