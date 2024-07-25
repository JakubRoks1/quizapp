package com.example.quizapp.service;

import com.example.quizapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testAddUser() {
//        // Given
//        User user = new User();
//        when(userRepository.save(user)).thenReturn(user);
//
//        // When
//        User result = userService.addUser(user);
//
//        // Then
//        assertThat(result).isEqualTo(user);
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        // Given
//        User user1 = new User();
//        User user2 = new User();
//        List<User> users = Arrays.asList(user1, user2);
//        when(userRepository.findAll()).thenReturn(users);
//
//        // When
//        List<User> result = userService.getAllUsers();
//
//        // Then
//        assertThat(result).isEqualTo(users);
//        verify(userRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetUserById() {
//        // Given
//        Long id = 1L;
//        User user = new User();
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        // When
//        Optional<User> result = userService.getUserById(id);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(user);
//        verify(userRepository, times(1)).findById(id);
//    }
//
//    @Test
//    public void testGetUserById_NotFound() {
//        // Given
//        Long id = 1L;
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When
//        Optional<User> result = userService.getUserById(id);
//
//        // Then
//        assertThat(result).isNotPresent();
//        verify(userRepository, times(1)).findById(id);
//    }
//
//    @Test
//    public void testUpdateUser() {
//        // Given
//        Long id = 1L;
//        User existingUser = new User();
//        User updatedDetails = new User();
//        updatedDetails.setUsername("testUsername");
//        updatedDetails.setEmail("email@example.com");
//
//        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
//        when(userRepository.save(existingUser)).thenReturn(existingUser);
//
//        // When
//        User result = userService.updateUser(id, updatedDetails);
//
//        // Then
//        assertThat(result.getUsername()).isEqualTo("testUsername");
//        assertThat(result.getEmail()).isEqualTo("email@example.com");
//        verify(userRepository, times(1)).findById(id);
//        verify(userRepository, times(1)).save(existingUser);
//    }
//
//    @Test
//    public void testUpdateUser_NotFound() {
//        // Given
//        Long id = 1L;
//        User updatedDetails = new User();
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When / Then
//        try {
//            userService.updateUser(id, updatedDetails);
//        } catch (RuntimeException e) {
//            assertThat(e.getMessage()).isEqualTo("User not found");
//        }
//        verify(userRepository, times(1)).findById(id);
//        verify(userRepository, times(0)).save(any(User.class));
//    }
//
//    @Test
//    public void testDeleteUser() {
//        // Given
//        Long id = 1L;
//        User user = new User();
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        // When
//        userService.deleteUser(id);
//
//        // Then
//        verify(userRepository, times(1)).findById(id);
//        verify(userRepository, times(1)).delete(user);
//    }
//
//    @Test
//    public void testDeleteUser_NotFound() {
//        // Given
//        Long id = 1L;
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When
//        userService.deleteUser(id);
//
//        // Then
//        verify(userRepository, times(1)).findById(id);
//        verify(userRepository, times(0)).delete(any(User.class));
//    }


}
