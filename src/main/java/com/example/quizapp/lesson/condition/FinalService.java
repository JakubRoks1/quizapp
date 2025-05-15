package com.example.quizapp.lesson.condition;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinalService {

    private final Optional<DebugService> debugService;
    private Calculator firstService;

    @Autowired
    public FinalService(Calculator c, Optional<DebugService> debugService) {
        this.firstService = c;
        this.debugService = debugService;
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
        debugService.ifPresent(DebugService::debug);
    }

    public int caluclate() {
        return firstService != null ? firstService.calc() : 0;
    }
}
