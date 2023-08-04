package com.iwor.spring.dto;

import com.iwor.spring.database.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreatEditDto {
    String username;
    String firstname;
    String lastname;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;
    Role role;
    Integer companyId;
}
