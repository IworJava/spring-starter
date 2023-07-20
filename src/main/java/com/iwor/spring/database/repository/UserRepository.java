package com.iwor.spring.database.repository;

import com.iwor.spring.database.pool.ConnectionPool;

public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool pool2) {
        this.connectionPool = pool2;
    }
}
