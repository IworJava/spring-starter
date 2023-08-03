package com.iwor.spring.integration.service;

import com.iwor.spring.config.DatabaseProperties;
import com.iwor.spring.dto.CompanyReadDto;
import com.iwor.spring.integration.IntegrationTestBase;
import com.iwor.spring.integration.annotation.IT;
import com.iwor.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class CompanyServiceIT extends IntegrationTestBase {

    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void getById() {
        var expected = new CompanyReadDto(COMPANY_ID, null);
        var actual = companyService.findById(COMPANY_ID);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}
