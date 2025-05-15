package com.example.quizapp.lesson.cproperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {Person.class})
public class ConfigApp {
}
