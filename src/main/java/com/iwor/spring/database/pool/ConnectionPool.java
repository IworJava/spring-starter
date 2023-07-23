package com.iwor.spring.database.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
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
        log.info("Init connection pool");
    }

    @PreDestroy
    public void destroy() {
        log.info("Close connection pool");
    }
}
