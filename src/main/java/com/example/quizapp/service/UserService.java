package com.example.quizapp.service;

import com.example.quizapp.entity.UserEntity;
import com.example.quizapp.json.UserJson;
import com.example.quizapp.mappers.UserMapper;
import com.example.quizapp.model.User;
import com.example.quizapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserJson addUser(UserJson userJson) {
        User user = userMapper.mapToUser(userJson);
        UserEntity userEntity = userMapper.mapToUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User savedUser = userMapper.mapToUser(savedUserEntity);
        return userMapper.mapToUserJson(savedUser);
    }

    public List<UserJson> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::mapToUser)
                .map(userMapper::mapToUserJson)
                .collect(Collectors.toList());
    }

    public Optional<UserJson> getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUser)
                .map(userMapper::mapToUserJson);
    }

    public UserJson updateUser(Long id, UserJson userJson) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userJson.getUsername());
                    existingUser.setEmail(userJson.getEmail());
                    UserEntity updatedUserEntity = userRepository.save(existingUser);
                    User updatedUser = userMapper.mapToUser(updatedUserEntity);
                    return userMapper.mapToUserJson(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
