package com.example.quizapp.lesson.condition;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalService {

    private Calculator firstService;

    @Autowired
    public FinalService(Calculator c) {
        this.firstService = c;
    }

//    @Autowired
//    public FinalService(ObjectProvider<FirstService> firstService) { // moze byc opcjonal
//        this.firstService = firstService.getIfAvailable();
//    }

//    @Autowired(required = false)
//    public FinalService(FirstService firstService) {
//        this.firstService = firstService;
//    }

    @PostConstruct
    public void a() {
        System.out.println("*************");
        System.out.println("*************");
        System.out.println("FinalService initialized");
        System.out.println("*************");
        System.out.println("*************");
        System.out.println(firstService);
    }

    public int caluclate() {
        return firstService != null ? firstService.calc() : 0;
    }
}
