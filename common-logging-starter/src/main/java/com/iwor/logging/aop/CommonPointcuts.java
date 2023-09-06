package com.iwor.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("within(com.iwor.*.service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

}
