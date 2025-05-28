package com.example.quizapp.lesson.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beans")
public class BeanController {

    private final Animal animal;

//    @Autowired
//    public BeanController(Animal dog) {
//        this.animal = dog;
//    }

//    @Autowired
//    public BeanController(@Qualifier("reksio") Animal animal) {
//        this.animal = animal;
//    }

//    @Autowired
//    public BeanController(List<Animal> animal) { // cala lista wszystkich beanow da sie tym sterowac za pomoca @Order
//        System.out.println(animal);
//        this.animal = animal.get(0);
//    }

    @Autowired
    public BeanController(@Qualifier("mruczek") Animal mruczek) { // cala lista wszystkich beanow da sie tym sterowac za pomoca @Order
        this.animal = mruczek;
    }

    @GetMapping
    public Animal a() {
        return animal;
    }
}
