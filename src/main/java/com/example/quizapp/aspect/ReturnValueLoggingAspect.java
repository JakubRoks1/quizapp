package com.example.quizapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReturnValueLoggingAspect {

    @AfterReturning(
            pointcut = "execution(public * com.example.quizapp.service..*(..))",
            returning = "result"
    )
    public void logReturnValue(JoinPoint joinPoint, Object result) {

        String className = joinPoint.getSignature()
                .getDeclaringType()
                .getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        System.out.println(
                "Method: " + className + "." + methodName
                        + " returned: " + result
        );

    }
}
