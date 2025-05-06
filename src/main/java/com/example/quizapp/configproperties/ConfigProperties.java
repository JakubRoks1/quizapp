package com.example.quizapp.configproperties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "config")
public record ConfigProperties(
        @NotNull String appName, //simple property
        @NotNull Gender defaultGender, // Enum mapping
        @Size(min = 1) List<String> Languages, // List mapping
        Map<String, String> metadata, // Map structure
        NestedConfig nestedConfig // Nested configuration properties


        ) {
}
