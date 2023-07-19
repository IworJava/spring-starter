package com.iwor.spring.database.repository;

import com.iwor.spring.bpp.Auditing;
import com.iwor.spring.bpp.InjectBean;
import com.iwor.spring.bpp.Transaction;
import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    private void init() {
        System.out.println("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}
