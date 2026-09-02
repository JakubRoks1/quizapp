package com.example.quizapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AClassArgumentsAspect {

    @Before("execution(* com.example.quizapp.service.A*.*(..))")

    public void logArguments(JoinPoint joinPoint) {

        String className = joinPoint.getSignature()
                .getDeclaringType()
                .getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        Object[] arguments = joinPoint.getArgs();

        System.out.println(
                "Class: " + className
                        + ", method: " + methodName
                        + ", arguments: " + Arrays.toString(arguments)
        );
    }
}
