package com.example.quizapp.service;

import com.example.quizapp.mappers.UserMapper;
import com.example.quizapp.model.User;
import com.example.quizapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository questionRepository,
                       UserMapper userMapper) {
        this.userRepository = questionRepository;
        this.userMapper = userMapper;
    }

    public User addUser(User user) {
        var userEntity = userMapper.mapToUserEntity(user);

        var saved = userRepository.save(userEntity);

        return userMapper.mapToUser(saved);
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    public User updateUser(Long id, User userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(userDetails.getUsername());
//                    user.setEmail(userDetails.getEmail());
//                    return userRepository.save(user);
//                }).orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.findById(id)
//                .ifPresent(userRepository::delete);
//    }
}
