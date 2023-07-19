package com.iwor.spring.database.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("pool")
public class ConnectionPool implements Ordered {

    private final String username;
    private final Integer poolSize;

    public ConnectionPool(@Value("${db.username}") String username,
                          @Value("${db.pool.size}") Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
    }

    @Override
    public int getOrder() {
        return poolSize != null
                ? poolSize
                : Integer.MAX_VALUE;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init connection pool");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Close connection pool");
    }
}
