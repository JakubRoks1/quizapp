package com.example.quizapp.lesson.cachetwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheMain2 {

    public static void main(String[] args) {
        System.out.println("xxx");
        SpringApplication.run(CacheMain2.class, args);
    }
}
