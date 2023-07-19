package com.iwor.spring.database.repository;

import com.iwor.spring.bpp.Auditing;
import com.iwor.spring.bpp.Transaction;
import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

//@Repository
@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool p;

    private final List<ConnectionPool> pools;

    public CompanyRepository(ConnectionPool p, List<ConnectionPool> pools) {
        this.p = p;
        this.pools = pools;
    }

    public ConnectionPool getP() {
        return p;
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

    @PostConstruct
    private void init() {
        System.out.println("init company repository");
    }
}
