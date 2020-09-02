package com.wen.awenboot.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/27 15:28
 */
@Aspect
@Service
@Slf4j
public class LogAspect {
    @Pointcut("execution(* *(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void start() {
        log.info("start");
    }

    @After("pointcut()")
    public void end() {
        log.info("end");
    }

    @AfterReturning("pointcut()")
    public void returnRet() {
        log.info("returnRet");
    }

    @AfterThrowing("pointcut()")
    public void exception() {
        log.info("exception");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        log.info("around");
        return proceed;
    }

    @PostConstruct
    private void init() {
        System.out.println(1111);
    }
}
