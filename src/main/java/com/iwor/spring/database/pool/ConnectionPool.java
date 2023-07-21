package com.iwor.spring.database.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("pool1")
public class ConnectionPool implements Ordered {

    @Value("${db.username}")
    private final String username;
    @Value("${db.pool.size}")
    private final Integer poolSize;

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
