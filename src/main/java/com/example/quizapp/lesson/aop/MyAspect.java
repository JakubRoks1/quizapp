package com.example.quizapp.lesson.aop;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class MyAspect {

    @PostConstruct
    public void init() {
        log.info("Aspekt powstał");
    }

//    @Pointcut("execution(* com.example.quizapp.lesson.aop.MyTestController.t*())")
//    public void testPointcut() {
//
//    }

//    @Before("testPointcut()")
    @Before(value = "execution(* com.example.quizapp.lesson.aop.MyTestController.o*())")
    public void before() {
        log.info("jestem przed czyms");
    }

    @After("execution(* com.example.quizapp.lesson.aop.MyTestController.o*())")
    public void after() {
        log.info("jestem po wszystkims");
    }

    @Around("@annotation(com.example.quizapp.lesson.aop.TimedLogged)")
    public Object aroundTimed(ProceedingJoinPoint joinPoint) throws Throwable {

        var now = LocalDateTime.now();
        log.warn("Czas rozpoczecia: {}", now);
        var result = joinPoint.proceed();
        var end = LocalDateTime.now();
        log.warn("Czas zakonczenia: {}, czas trwania {}", end, Duration.between(now, end).toNanos());

        return result;
    }

//    @Before(value = "execution(* com.example.quizapp.lesson.aop.MyTestController.a4(..)) && args(value, value2)")
//    public void before2(JoinPoint joinPoint, int value, int value2) {
//        log.info("jestem przed a4 :{} {}", value, value2);
////        log.info("{}", joinPoint.getArgs());
//    }

    @Around(value = "execution(* com.example.quizapp.lesson.aop.MyTestController.a4(..)) && args(value, value2)")
    public Object around2(ProceedingJoinPoint proceedingJoinPoint, int value, int value2) throws Throwable {
        log.info("jestem przed a4-around :{} {}", value, value2);
        var result = proceedingJoinPoint.proceed(new Object[]{0, 0});
//        log.info("{}", joinPoint.getArgs());
        return result;
    }
}
