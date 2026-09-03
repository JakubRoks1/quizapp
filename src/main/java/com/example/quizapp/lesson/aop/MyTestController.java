package com.example.quizapp.lesson.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class MyTestController {

    private final MyService myService;

    public MyTestController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping
    public void other() {

        log.info("jestem w kontrolerze");

    }

    @GetMapping("/2")
    @TimedLogged
    public void other2() {

        log.info("jestem w kontrolerze drugim");
        myService.fetch();

    }

    @GetMapping("/3")
    public void a3() {

        log.info("jestem w kontrolerze trzecim");
        myService.fetch();

    }

    @GetMapping("/4")
    public int a4(@RequestParam int value, @RequestParam int value2) {

        log.info("jestem w kontrolerze czwartym " + value);
        return value * 20;
    }
}
