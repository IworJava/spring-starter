package com.iwor.spring.dto;

import com.iwor.spring.database.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserCreatEditDto {
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
    Integer companyId;
}
