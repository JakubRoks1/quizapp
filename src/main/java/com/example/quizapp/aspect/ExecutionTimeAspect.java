package com.example.quizapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("execution(public * com.example.quizapp.service..*(..))")
    public Object measureExcecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        long endTime = System.nanoTime();

        double executionTime = (endTime - startTime) / 1_000_000.0;

        System.out.println(
                "Method: " + joinPoint.getSignature().getName()
                        + " execution time: " + executionTime + " ms"
        );

        return result;

    }
}
