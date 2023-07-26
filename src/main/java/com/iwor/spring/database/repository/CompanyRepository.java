package com.iwor.spring.database.repository;

import com.iwor.spring.bpp.Auditing;
import com.iwor.spring.bpp.Transaction;
import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.pool.ConnectionPool;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;

    private final List<ConnectionPool> pools;

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(Company.builder()
                .id(id)
                .build());
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }

    @PostConstruct
    private void init() {
        log.info("init company repository");
    }
}
