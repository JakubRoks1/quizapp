package com.example.quizapp.config;

import com.example.quizapp.model.Custom;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({Custom.class})
public class CustomConfig {
}
