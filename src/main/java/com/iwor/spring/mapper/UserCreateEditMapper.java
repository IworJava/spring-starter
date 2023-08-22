package com.iwor.spring.mapper;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.database.repository.CompanyRepository;
import com.iwor.spring.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        return copy(fromObject, toObject);
    }

    @Override
    public User map(UserCreateEditDto object) {
        var user = new User();
        return copy(object, user);
    }

    private User copy(UserCreateEditDto dto, User user) {
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setBirthDate(dto.getBirthDate());
        user.setRole(dto.getRole());
        user.setCompany(getCompany(dto.getCompanyId()));

        Optional.ofNullable(dto.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(dto.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .map(MultipartFile::getOriginalFilename)
                .ifPresent(user::setImage);
//                .ifPresent(img -> user.setImage(img.getOriginalFilename()));
        return user;
    }

    private Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
