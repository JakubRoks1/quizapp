package com.example.quizapp.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final OwnerService ownerService;

    @PostMapping("/m1")
    public void m1() {
        ownerService.create();
    }

    @PostMapping("/m2")
    public void m2() {
        ownerService.trans();
    }

    @PostMapping("/m3")
    public void m3() {
        ownerService.m3();
    }

    @PostMapping("/m4")
    public void m4() {
        ownerService.m4();
    }

    @PostMapping("/m5")
    public void m5() {
        ownerService.m5();
    }
}
