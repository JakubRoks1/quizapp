package com.example.quizapp.mappers;

import com.example.quizapp.entity.ScoreEntity;
import com.example.quizapp.entity.UserEntity;
import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.json.UserJson;
import com.example.quizapp.model.Score;
import com.example.quizapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserEntity mapToUserEntity(User user);

    User mapToUser(UserEntity userEntity);

    UserJson mapToUserJson(User user);
}
