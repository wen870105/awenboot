/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.wen.awenboot.aop;

import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.enums.ApiDetailCntEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author wsy48420
 * @version $Id: ServiceTimerAspect.java, v 0.1 2018年12月21日 下午5:57:17 wsy48420 Exp $
 */
@Aspect
@Service
public class ServiceTimerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTimerAspect.class);

    @PostConstruct
    private void init() {
        logger.info("===");
    }


    @Pointcut(value = "execution(public * com.wen.awenboot.controller.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object beforeMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = null;
        long start = System.currentTimeMillis();
        try {
            TagDetailCntCache.getInstance().incrementAndGet(ApiDetailCntEnum.WEB.getCode());
            obj = pjp.proceed();
            return obj;
        } catch (Throwable t) {
            throw t;
        } finally {
        }
    }
}