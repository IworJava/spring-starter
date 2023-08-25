package com.iwor.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(2)
@Component
public class SecondAspect {

    @Around(value = "com.iwor.spring.aop.FirstAspect.anyFindByIdServiceMethod() " +
                    "&& args(id) " +
                    "&& target(service)",
            argNames = "joinPoint,id,service")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object id, Object service) throws Throwable {
        log.info("AROUND Before - invoked findById method in class {}, with id {}", service, id);
        Object result;
        try {
            result = joinPoint.proceed();
            log.info("AROUND After returning - invoked findById method in class {}, result {}", service, result);
            return result;
        } catch (Throwable e) {
            log.info("AROUND After throwing - invoked findById method in class {}, exception {}: {}", service, e.getClass(), e.getMessage());
            throw e;
        } finally {
            log.info("AROUND After (finally) - invoked findById method in class {}", service);
        }
    }
}
