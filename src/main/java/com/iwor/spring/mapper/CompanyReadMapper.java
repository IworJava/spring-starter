package com.iwor.spring.mapper;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto map(Company company) {
        return new CompanyReadDto(
                company.getId(),
                company.getName()
        );
    }
}
