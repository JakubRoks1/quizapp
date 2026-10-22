package com.example.quizapp.lesson.internationalization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/i18n")
@RequiredArgsConstructor
public class Controller {

    private final MessageSource messageSource;

    @GetMapping
    public String hello(@RequestParam String name) {
        var locale = LocaleContextHolder.getLocale();
        System.out.println(locale);
        System.out.println(messageSource.getMessage("hello", new Object[]{name}, locale));
        return messageSource.getMessage("hello", new Object[]{name}, locale);
    }

    @GetMapping("/bye")
    public String bye() {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("bye", null, locale);
    }

    @GetMapping("/apples")
    public String apples(@RequestParam Integer count) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("apples", new Object[]{count, count}, locale);
    }
}
