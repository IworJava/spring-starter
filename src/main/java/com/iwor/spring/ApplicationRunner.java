package com.iwor.spring;

import com.iwor.spring.database.pool.ConnectionPool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("application.xml");
        var pool1 = context.getBean("pool1", ConnectionPool.class);
        System.out.println(pool1);
    }
}
