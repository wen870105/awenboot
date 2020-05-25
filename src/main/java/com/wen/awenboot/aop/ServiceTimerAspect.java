/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.wen.awenboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author wsy48420
 * @version $Id: ServiceTimerAspect.java, v 0.1 2018年12月21日 下午5:57:17 wsy48420 Exp $
 */
@Aspect
@Service
public class ServiceTimerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTimerAspect.class);

    /**
     * @param pjp
     * @return
     * @throws Throwable
     */
//    @Around(value = "execution(* *(..)) && @annotation(serviceTimerMetadata)", argNames = "pjp,serviceTimerMetadata")
    @Around(value = "execution(* com.wen.springboot.awenboot.*.*(..))")
    public Object doIndicator(final ProceedingJoinPoint pjp) throws Throwable {

//        preProcess(pjp, serviceTimerMetadata);
        Object obj = null;
        long start = System.currentTimeMillis();
        try {

            System.out.println("1111111" + pjp.getArgs());
            obj = pjp.proceed();
            return obj;
        } catch (Throwable t) {
//            processExceptionCallback(pjp, serviceTimerMetadata, t);
            throw t;
        } finally {
//            postProcess(pjp, serviceTimerMetadata, obj, start);
        }

    }

//    private void preProcess(ProceedingJoinPoint pjp, ServiceTimerMetadata serviceTimerMetadata) {
//
//    }
//
//    private void postProcess(ProceedingJoinPoint pjp, ServiceTimerMetadata serviceTimerMetadata, Object obj, long start) {
//        if (serviceTimerMetadata.asyncWriteResponseLog()) {
//            cb(obj, pjp, serviceTimerMetadata, System.currentTimeMillis() - start);
//        } else {
//            LoggerUtils.info(logger, "[{}]耗时: {}ms,响应参数:{}", serviceTimerMetadata.name(), System.currentTimeMillis() - start, JSON.toJSONString(obj));
//        }
//    }
//
//    private Object processExceptionCallback(ProceedingJoinPoint pjp, ServiceTimerMetadata serviceTimerMetadata, Throwable t) {
//        return null;
//    }
//
//    /**
//     * @param pjp
//     * @param serviceTimerMetadata
//     */
//    private void cb(Object obj, ProceedingJoinPoint pjp, ServiceTimerMetadata serviceTimerMetadata, long elapsedTime) {
//        if (StringUtils.isNotBlank(serviceTimerMetadata.callback())) {
//            LogCallback cb = SpringContextUtil.getBean(serviceTimerMetadata.callback(), LogCallback.class);
//            if (cb != null) {
//                try {
//                    cb.callback(obj, pjp, serviceTimerMetadata, elapsedTime);
//                } catch (Exception e) {
//                    LoggerUtils.info(logger, "", e);
//                }
//            }
//        }
//    }
}