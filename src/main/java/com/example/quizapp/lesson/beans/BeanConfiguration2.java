package com.example.quizapp.lesson.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
public class BeanConfiguration2 {

    @PostConstruct
    public void pc() {
        System.out.println("BeanConfiguration2");
    }

    @Bean
    @Order(5)
    @Lazy
    public Animal pepa() {
        System.out.println("Tworzę Pepę");
        return new Animal("pig", "Pepa");
    }
}
