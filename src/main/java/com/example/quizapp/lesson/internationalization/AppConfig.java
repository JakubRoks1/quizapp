package com.example.quizapp.lesson.internationalization;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

//    @Bean
//    public MessageSource messageSource() {
//        var r = new ReloadableResourceBundleMessageSource();
//        r.setBasename("classpath:constraints");
//        r.setUseCodeAsDefaultMessage(true);
////        r.setCacheSeconds(10);
//        return r;
//    }

//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("lang");
//        return interceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
}
