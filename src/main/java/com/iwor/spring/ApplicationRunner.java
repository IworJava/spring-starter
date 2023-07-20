package com.iwor.spring;

import com.iwor.spring.config.ApplicationConfiguration;
import com.iwor.spring.database.entity.Company;
import com.iwor.spring.database.repository.CrudRepository;
import com.iwor.spring.database.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.getEnvironment().setActiveProfiles("web", "prod");
        context.refresh();

        CrudRepository<Integer, Company> companyRepository = context.getBean("companyRepository", CrudRepository.class);
        var userRepository = context.getBean("userRepository", UserRepository.class);

        var company = companyRepository.findById(5);
        System.out.println(company);
        companyRepository.delete(company.get());
    }
}
