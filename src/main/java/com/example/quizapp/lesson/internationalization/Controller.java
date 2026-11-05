package com.example.quizapp.lesson.internationalization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/i18n") // l10n
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

    @PostMapping
    public InputDto post(@RequestBody @Validated InputDto inputDto) {

        return inputDto;
    }

    @GetMapping("/error")
    public String error() {
        throw new MyAppException(MyAppException.Code.NOT_USER_FOUND);
    }

    @GetMapping("/error2")
    public String error2() {
        throw new MyAppException(MyAppException.Code.INTERNAL_ERROR);
    }
}
