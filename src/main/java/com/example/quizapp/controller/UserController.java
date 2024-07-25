package com.example.quizapp.controller;

import com.example.quizapp.json.UserJson;
import com.example.quizapp.mappers.UserMapper;
import com.example.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserJson createUser(@RequestBody UserJson userJson) {
        var saved = userService.addUser(userMapper.mapToUser(userJson));
        return userMapper.mapToUserJson(saved);
    }
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> ResponseEntity.ok().body(user))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(userDetails.getUsername());
//                    user.setEmail(userDetails.getEmail());
//                    User updatedUser = userRepository.save(user);
//                    return ResponseEntity.ok().body(updatedUser);
//                }).orElse(ResponseEntity.notFound().build());
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    userRepository.delete(user);
//                    return ResponseEntity.ok().build();
//                }).orElse(ResponseEntity.notFound().build());
//    }


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
