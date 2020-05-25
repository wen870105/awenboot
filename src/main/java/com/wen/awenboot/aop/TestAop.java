//package com.wen.springboot.awenboot.aop;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Service;
//
///**
// * @author wen
// * @version 1.0
// * @date 2020/5/21 10:38
// */
//@Aspect
//@Service
//public class TestAop {
//
//    @Pointcut("@annotation(com.space.aspect.anno.SysLog)")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        long beginTime = System.currentTimeMillis();
//        Object result = point.proceed();
//        long time = System.currentTimeMillis() - beginTime;
//        try {
//        } catch (Exception e) {
//        }
//        return result;
//    }
//}
