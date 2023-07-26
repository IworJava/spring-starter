package com.iwor.spring.integration.database.repository;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.SpecHints;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
@IT
class CompanyRepositoryTest {

    private final EntityManager em;
    private final TransactionTemplate transactionTemplate;

    @Test
    void findById() {
        Map<String, Object> properties = Map.of(SpecHints.HINT_SPEC_FETCH_GRAPH,
                em.getEntityGraph("withLocales"));
        var company = em.find(Company.class, 1, properties);

        assertNotNull(company);
        assertThat(company.getLocales()).hasSize(2);
    }

    /**
     * TransactionTemplate позволяет управлять транзакцией при отсутствующей @Transactional.
     * Без транзакции выбросится LazyInitializationException.
     */
    @Test
    void findById2() {
        transactionTemplate.executeWithoutResult(tx -> {
            var company = em.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
    }

    @Test
    void test() {
        var company = Company.builder()
                .name("Apple")
                .build();
        em.persist(company);

        assertThat(company.getId()).isNotNull();
    }
}