package com.iwor.spring.database.repository;

import com.iwor.spring.bpp.InjectBean;
import com.iwor.spring.database.pool.ConnectionPool;

public class CompanyRepository {

    @InjectBean
    private ConnectionPool connectionPool;

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
