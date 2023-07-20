package com.iwor.spring.database.repository;

import com.iwor.spring.database.pool.ConnectionPool;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool pool2) {
        this.connectionPool = pool2;
    }
}
