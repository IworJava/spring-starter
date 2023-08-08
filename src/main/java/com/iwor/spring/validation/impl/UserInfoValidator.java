package com.iwor.spring.validation.impl;

import com.iwor.spring.dto.UserCreatEditDto;
import com.iwor.spring.validation.UserInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreatEditDto> {

    @Override
    public boolean isValid(UserCreatEditDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getFirstname()) || StringUtils.hasText(value.getLastname());
    }
}
