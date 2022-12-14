package com.spring.common;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    static Logger logger=Logger.getLogger(LogAspect.class);

    // 定义一个切入点
    @Pointcut("execution(* com.spring.service.*.*(..))")
    public void pc1(){

    }

    // 返回通知
    @AfterReturning(value = "pc1()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        String name = jp.getSignature().toShortString();
        logger.info("info:"+name+ "方法返回值为：" + result);
    }

    // 异常通知
    @AfterThrowing(value = "pc1()", throwing = "e")
    public void afterThrowing(JoinPoint jp, Exception e) {
        String name = jp.getSignature().toShortString();
        logger.error("error:"+name + "方法抛异常了，异常是：" + e.getMessage());
    }

    // 环绕通知
    @Around("pc1()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().toShortString();
        // 统计方法执行时间
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("info:"+name + "方法执行时间为：" + (end - start) + " ms");
        return result;
    }
}