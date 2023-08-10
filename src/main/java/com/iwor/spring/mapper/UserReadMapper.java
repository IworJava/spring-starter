package com.iwor.spring.mapper;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User user) {
        var companyReadDto = Optional.ofNullable(user.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirthDate(),
                user.getImage(),
                user.getRole(),
                companyReadDto
        );
    }
}
