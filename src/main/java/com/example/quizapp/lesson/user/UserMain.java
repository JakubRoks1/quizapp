package com.example.quizapp.lesson.user;

import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@SpringBootApplication
public class UserMain {

    private final PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    private final Map<String, User> savedUsers = new HashMap<>();
    private final Map<String, User> loggedUsers = new HashMap<>();

    public static void main(String[] args) {

//        encoder();


//
        UserMain userMain = new UserMain();
//
        userMain.createUser("mateusz", "123"); //-1424436592
        userMain.createUser("mariusz", "xyz"); //119193
//
        System.out.println(userMain.savedUsers);
//
        var key = userMain.authenticate("mateusz", "123").orElseThrow(() -> new RuntimeException("Authentication failed"));
        var key2 = userMain.authenticate("mariusz", "xyz").orElseThrow(() -> new RuntimeException("Authentication failed"));
        userMain.securedFunction(key2);
        userMain.securedFunction("cosinnego");
        userMain.securedFunction(key);
//        userMain.authenticate("mateusz", "abc");
//
//        System.out.println(userMain.authenticate("mateusz", "inne"));
//
//        userMain.crackPassword("mateusz");


    }

    private static void encoder() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        Pbkdf2PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
//        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        var p1 = passwordEncoder.encode("abc123");
        var p2 = passwordEncoder.encode("abc123");
        var p3 = passwordEncoder.encode("abc123");
        var p4 = passwordEncoder.encode("abc123");
        var p5 = passwordEncoder.encode("abc123");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);


        System.out.println(passwordEncoder.matches("abc123", p1));
        System.out.println(passwordEncoder.matches("abc123", p2));
        System.out.println(passwordEncoder.matches("abc1sdsd23", p3));
        System.out.println(passwordEncoder.matches("abc123", p4));
        System.out.println(passwordEncoder.matches("abc123", p5));
    }


    public void createUser(String login, String password) {
        savedUsers.put(login, new User(login, passwordEncoder.encode(password), true));
    }

    public Optional<String> authenticate(String login, String inputPassword) {
        val user = savedUsers.get(login);
        if (user == null) {
            return Optional.empty();
        }


        if (user.isActive() && passwordEncoder.matches(inputPassword, user.password())) {
            var uuid = UUID.randomUUID().toString();
            System.out.println("Witamy " + login + " " + uuid);
            loggedUsers.put(uuid, user);
            return Optional.of(uuid);
        }

        return Optional.empty();
    }

    public void crackPassword(String login) {
        var user = savedUsers.get(login);

        var start = System.currentTimeMillis();
        System.out.println("start " + start);


        for (int i = 0; i < 200; i++) {
            System.out.println("sprawdzam " + i + " " + System.currentTimeMillis());
            if (passwordEncoder.matches(String.valueOf(i), user.password())) {
                System.out.println("Złamane hasło " + i);
                System.out.println(System.currentTimeMillis() - start);
                return;
            }
        }

    }

    private void securedFunction(String key) {
        // autoryzacja
        System.out.println(" to jest zabezpieczona funkcja przekazanano klucz " + key);

        var user = loggedUsers.get(key);

        if (user == null) {
            System.out.println("Nie masz dostępu");
            return;
        }

        System.out.println("Witaj w panelu admina " + user.login());
    }
}
