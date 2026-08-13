package com.example.quizapp.lesson.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AopStarter {

    public static void main(String[] args) {
        SpringApplication.run(AopStarter.class, args);
    }
}
