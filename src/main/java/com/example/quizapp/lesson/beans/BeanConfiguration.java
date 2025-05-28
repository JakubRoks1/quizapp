package com.example.quizapp.lesson.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

@Configuration
@DependsOn("beanConfiguration2")
public class BeanConfiguration {

    @PostConstruct
    public void pc() {
        System.out.println("BeanConfiguration");
    }

    @Bean(name = {"reksio", "dog"})
    @Order(8)
    @Primary // jak nie wie to zawsze wybierz primary, ale wtedy nie działają nazwy parametów
    @Lazy
    public Animal reksioConstruction() {
        System.out.println("Tworzę reksia");
        return new Animal("dog", "Reksio");
    }

    @Bean
    @Order(2)
    @Lazy
    public Animal mruczek() {
        System.out.println("Tworzę mruczka");
        return new Animal("cat", "Mruczek");
    }
}
