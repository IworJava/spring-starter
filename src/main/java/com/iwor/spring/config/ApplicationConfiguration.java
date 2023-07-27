package com.iwor.spring.config;

import com.iwor.spring.database.pool.ConnectionPool;
import com.iwor.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration(proxyBeanMethods = true)
//@PropertySource("classpath:application.properties")
@Import(WebConfiguration.class)
//@ComponentScan(
//        basePackages = "com.iwor.spring",
//        useDefaultFilters = false,
//        includeFilters = {
//                @Filter(type = FilterType.ANNOTATION, value = Component.class),
//                @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
//                @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//        }
//)
public class ApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-name", 25);
    }
}
