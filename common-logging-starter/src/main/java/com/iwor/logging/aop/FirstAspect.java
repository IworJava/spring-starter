package com.iwor.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
public class FirstAspect {

    @Pointcut("execution(public * com.iwor.*.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

//    @Before("execution(public * com.iwor.spring.service.*Service.findById(*))")
    @Before(value = "anyFindByIdServiceMethod() " +
                    "&& args(id) " +
                    "&& target(service) " +
                    "&& this(serviceProxy)" +
                    "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("Before - invoked findById method in class {}, with id {}", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() " +
                            "&& target(service)",
                    returning = "result",
                    argNames = "result,service")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("After returning - invoked findById method in class {}, result {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() " +
                           "&& target(service)",
                   throwing = "e")
    public void addLoggingAfterThrowing(Object service, Throwable e) {
        log.info("After throwing - invoked findById method in class {}, exception {}: {}", service, e.getClass(), e.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("After (finally) - invoked findById method in class {}", service);
    }
}
