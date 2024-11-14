package com.example.quizapp.json.converter;

import com.example.quizapp.json.FetchMode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FetchModeConverter implements Converter<String, FetchMode> {
    @Override
    public FetchMode convert(String source) {
        return FetchMode.fromString(source);
    }
}
