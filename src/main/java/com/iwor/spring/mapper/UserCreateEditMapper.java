package com.iwor.spring.mapper;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.database.repository.CompanyRepository;
import com.iwor.spring.dto.UserCreatEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserCreateEditMapper implements Mapper<UserCreatEditDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreatEditDto fromObject, User toObject) {
        return copy(fromObject, toObject);
    }

    @Override
    public User map(UserCreatEditDto object) {
        var user = new User();
        return copy(object, user);
    }

    private User copy(UserCreatEditDto dto, User user) {
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setBirthDate(dto.getBirthDate());
        user.setRole(dto.getRole());
        user.setCompany(getCompany(dto.getCompanyId()));
        return user;
    }

    private Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
