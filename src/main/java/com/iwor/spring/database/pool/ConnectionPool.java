package com.iwor.spring.database.pool;

import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;

public class ConnectionPool implements Ordered {

    private String username;
    private Integer poolSize;
    private List<Object> args;
    private Map<String, Object> properties;

    public ConnectionPool() {
    }

    public ConnectionPool(String username,
                          Integer poolSize,
                          List<Object> args,
                          Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public int getOrder() {
        return poolSize != null
                ? poolSize
                : Integer.MAX_VALUE;
    }
}
