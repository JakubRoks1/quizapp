package com.example.quizapp.config;

import com.example.quizapp.model.Custom;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    @ConfigurationProperties(prefix = "custom")
    public Custom customProperties() {
        return new Custom();
    }
}
