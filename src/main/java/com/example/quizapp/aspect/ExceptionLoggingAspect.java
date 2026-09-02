package com.example.quizapp.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(public * com.example.quizapp..*(..))", throwing = "exception")

    public void logException(JoinPoint joinPoint, Throwable exception) {

        String className = joinPoint.getSignature()
                .getDeclaringType()
                .getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        System.out.println(
                "Method: " + className + "." + methodName
                        + " has thrown an exception: "
                        + exception.getClass().getSimpleName()
        );
    }
}
