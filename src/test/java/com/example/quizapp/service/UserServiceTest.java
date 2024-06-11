package com.example.quizapp.service;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.User;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        // Given
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // When
        User result = userService.addUser(user);

        // Then
        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUsers() {
        // Given
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertThat(result).isEqualTo(users);
        verify(userRepository, times(1)).findAll();
    }


}
