package com.example.quizapp.lesson.aop;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyService {

    @SneakyThrows
    @TimedLogged
    public void fetch() {
        Thread.sleep(new Random().nextInt(2000) + 1000);
    }
}
