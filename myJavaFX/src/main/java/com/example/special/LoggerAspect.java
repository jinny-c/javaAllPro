package com.example.special;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
@ComponentScan("com.example.special")
public class LoggerAspect {
    @Pointcut("execution(* com.example..*(..))")
    public void executionService() {
    }

    //@Around("execution(* com.example.*.*(..))")
    @Around("executionService()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("{} executed in {} ms", joinPoint.getSignature(), (endTime - startTime));
        return result;
    }
}