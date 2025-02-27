package com.example.quizapp.mappers;

import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.json.ErrorJson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ErrorMapper {
    @Mapping(target = "code", source = "exceptionType.code")
    ErrorJson toError(QuizAppException exception);
}
