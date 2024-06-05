package com.example.quizapp.config;

import com.example.quizapp.model.User;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public User user1() {
        System.out.println("tworzę usera");
        val user = new User();
        user.setId(1L);
        user.setPassword("abc");
        user.setUsername("Mateusz");
        return user;
    }
}
