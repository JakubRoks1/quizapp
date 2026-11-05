package com.example.quizapp.lesson.internationalization;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record InputDto(
    @NotNull(message = "{validation.name}") String name,
    @Min(value = 20, message = "{validation.age}") Integer age
) {}
