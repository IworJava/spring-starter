package com.iwor.spring.dto;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.validation.UserInfo;
import com.iwor.spring.validation.group.Creation;
import com.iwor.spring.validation.group.Update;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = {Creation.class})
public class UserCreateEditDto {

    @NotEmpty
    @Email
    String username;

    @NotBlank(groups = Creation.class)
    String rawPassword;

    @Size(min = 1, max = 64, groups = Update.class)
    String firstname;

    @Size(min = 1, max = 64, groups = Update.class)
    String lastname;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;

    MultipartFile image;

    @NotNull
    Role role;

    @NotNull
    @Positive
    Integer companyId;
}
