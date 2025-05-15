package com.example.quizapp.lesson.cproperties;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class MyController {

    @Value("${custom.number}")
    private int liczba;

    @Autowired
    private Person person;

    @GetMapping
    public int a() {
        if (liczba > 5) {
            throw new RuntimeException("Invalid Conf");
        }
        return liczba;
    }

    @PostConstruct
    public void init() {
        System.out.println("liczba: " + liczba);
        System.out.println("person: " + person);
//        if (liczba > 5) {
//            throw new RuntimeException("Invalid Conf z post const");
//        }
    }

    @GetMapping("/person")
    public Person b() {
        return person;
    }
}
