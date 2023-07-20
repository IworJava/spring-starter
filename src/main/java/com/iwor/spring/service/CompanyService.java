package com.iwor.spring.service;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.repository.CrudRepository;
import com.iwor.spring.dto.CompanyReadDto;
import com.iwor.spring.listener.entity.AccessType;
import com.iwor.spring.listener.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyService {

    private final CrudRepository<Integer, Company> companyRepository;
    private final ApplicationEventPublisher eventPublisher;


    public CompanyService(CrudRepository<Integer, Company> companyRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.eventPublisher = eventPublisher;
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CompanyReadDto(entity.id());
                });
    }
}
