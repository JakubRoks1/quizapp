package com.example.quizapp.configproperties;

import jakarta.validation.constraints.NotNull;

public record NestedConfig(
        @NotNull String groupName,
        int groupSize
) {
}
