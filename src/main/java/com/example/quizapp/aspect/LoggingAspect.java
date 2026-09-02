package com.example.quizapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public LoggingAspect() {
        System.out.println(">>> LOGGING ASPECT CREATED");
    }

    @Before("execution(* com.example.quizapp.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println(">>> AOP triggered for method: " + joinPoint.getSignature().getName());
    }
}
