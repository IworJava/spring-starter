package com.iwor.spring;

import com.iwor.spring.config.ApplicationConfiguration;
import com.iwor.spring.service.CompanyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.getEnvironment().setActiveProfiles("web", "prod");
        context.refresh();

        var companyService = context.getBean(CompanyService.class);
        System.out.println(companyService.findById(5));
    }
}
