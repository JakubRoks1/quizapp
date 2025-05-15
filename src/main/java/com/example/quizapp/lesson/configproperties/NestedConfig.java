package com.example.quizapp.lesson.configproperties;

import jakarta.validation.constraints.NotNull;

public record NestedConfig(
        @NotNull String groupName,
        int groupSize
) {
}
