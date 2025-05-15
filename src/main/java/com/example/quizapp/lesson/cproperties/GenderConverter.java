package com.example.quizapp.lesson.cproperties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String source) {
        if ("F".equals(source)) {
            return Gender.FEMALE;
        } else if ("M".equals(source)) {
            return Gender.MALE;
        } else {
            throw new IllegalArgumentException("Unknown gender: " + source);
        }
    }
}
