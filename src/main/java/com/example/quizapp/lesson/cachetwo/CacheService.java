package com.example.quizapp.lesson.cachetwo;

import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Cacheable("books")
public class CacheService {

    @SneakyThrows
    public String getRandomBook() {

        System.out.println("jestem w metodzie");
        Thread.sleep(5000);
        return "Władca pierścieni";
    }

    @SneakyThrows
    public String getRandomBook2(int i) {

        System.out.println("jestem w metodzie");
        Thread.sleep(5000);
        return "Władca pierścieni";
    }
}
