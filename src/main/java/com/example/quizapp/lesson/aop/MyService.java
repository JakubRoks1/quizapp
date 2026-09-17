package com.example.quizapp.lesson.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class MyService {

    @SneakyThrows
    @TimedLogged
    public void fetch() {
        log.info("siema z fetcha!");
        Thread.sleep(new Random().nextInt(2000) + 1000);
    }
}
