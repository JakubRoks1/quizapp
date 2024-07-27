package com.example.quizapp.controller;

import com.example.quizapp.entity.UserEntity;
import com.example.quizapp.json.UserJson;
import com.example.quizapp.mappers.UserMapper;
import com.example.quizapp.model.User;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserJson createUser(@RequestBody UserJson userJson) {
        var saved = userService.addUser(userMapper.mapToUser(userJson));
        return userMapper.mapToUserJson(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<User> users = userEntities.stream()
                    .map(userMapper::mapToUser)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        return UserRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(userDetails.getUsername());
//                    user.setEmail(userDetails.getEmail());
//                    User updatedUser = userRepository.save(user);
//                    return ResponseEntity.ok().body(updatedUser);
//                }).orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }


//    @PostMapping("/create")
//    public ResponseEntity<User> createUser(
//            @RequestParam Long id,
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam(required = false) String email) {
//
//        User user = new User();
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        User savedUser = userRepository.save(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
}
