package com.iwor.spring;

import com.iwor.spring.database.repository.CrudRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("application.xml");
        var companyRepository = context.getBean("companyRepository", CrudRepository.class);
        var company = companyRepository.findById(5);
        System.out.println(company);
        companyRepository.delete(company.get());
    }
}
