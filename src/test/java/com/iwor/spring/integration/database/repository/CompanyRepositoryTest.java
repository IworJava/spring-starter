package com.iwor.spring.integration.database.repository;

import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.repository.CompanyRepository;
import com.iwor.spring.database.repository.UserRepository;
import com.iwor.spring.integration.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.SpecHints;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase {

    private final EntityManager em;
    private final TransactionTemplate transactionTemplate;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Test
    void checkFindByQueries() {
        var company = companyRepository.findByName("Google");
        companyRepository.findAllByNameContainingIgnoreCase("OO");
    }

    @Test
    void deleteCompanyEmpty() {
        var companyId = companyRepository.save(
                Company.builder().name("New Company").build()
        ).getId();
        var maybeCompany = companyRepository.findById(companyId);

        assertThat(maybeCompany).isPresent();
        companyRepository.delete(maybeCompany.get());
        companyRepository.flush();
        assertThat(companyRepository.findById(companyId)).isEmpty();
    }

    @Test
    void deleteCompanyNotEmpty() {
        Integer companyId = 1;
        var maybeCompany = companyRepository.findById(companyId);
        assertThat(maybeCompany).isPresent();
        var company = maybeCompany.get();
        var users = company.getUsers();
        assertThat(users).isNotEmpty();
        var userId = users.get(0).getId();
        assertThat(userId).isNotNull();

        companyRepository.delete(company);
        companyRepository.flush();

        assertThat(companyRepository.findById(companyId)).isEmpty();
        var maybeUser = userRepository.findById(userId);
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getCompany()).isNull();
    }

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