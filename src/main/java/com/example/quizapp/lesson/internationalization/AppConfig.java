package com.example.quizapp.lesson.internationalization;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfig {

//    @Bean
    public MessageSource messageSource() {
        var r = new ReloadableResourceBundleMessageSource();
        r.setBasename("classpath:constraints");
        r.setUseCodeAsDefaultMessage(true);
//        r.setCacheSeconds(10);
        return r;
    }
}
