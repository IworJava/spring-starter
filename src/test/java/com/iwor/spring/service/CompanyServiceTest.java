package com.iwor.spring.service;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.repository.CompanyRepository;
import com.iwor.spring.dto.CompanyReadDto;
import com.iwor.spring.listener.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    private static final Integer COMPANY_ID = 1;

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(Company.builder()
                        .id(COMPANY_ID)
                        .build()))
                .when(companyRepository).findById(COMPANY_ID);

        var expected = new CompanyReadDto(COMPANY_ID, null);
        var actual = companyService.findById(COMPANY_ID);

        Mockito.verify(eventPublisher).publishEvent(Mockito.any(EntityEvent.class));
        Mockito.verify(companyRepository).findById(COMPANY_ID);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}
