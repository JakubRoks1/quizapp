package com.example.quizapp.mappers;

import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.json.ErrorJson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ErrorMapper {
    @Mapping(target = "code", source = "code")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "timestamp", source = "timestamp")
    ErrorJson toError(QuizAppException exception);
}
