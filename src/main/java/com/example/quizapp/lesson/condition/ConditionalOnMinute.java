package com.example.quizapp.lesson.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMinuteCondition.class)
public @interface ConditionalOnMinute {
    int value();
}
