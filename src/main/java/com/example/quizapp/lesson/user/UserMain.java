package com.example.quizapp.lesson.user;

import lombok.val;

import java.util.HashMap;
import java.util.Map;

//@SpringBootApplication
public class UserMain {

    private final Map<String, User> savedUsers = new HashMap<>();

    public static void main(String[] args) {
        UserMain userMain = new UserMain();

        userMain.createUser("mateusz", "123784"); //-1424436592
        userMain.createUser("mariusz", "xyz"); //119193

        System.out.println(userMain.savedUsers);

        System.out.println(userMain.authenticate("mateusz", "12378"));
        System.out.println(userMain.authenticate("mateusz", "-1424436592"));

        System.out.println(userMain.authenticate("mateusz", "inne"));

        userMain.crackPassword("mateusz");



    }

    private static String passwordHashingFunction(String password) {
        String salt = "213131";



        return String.valueOf(password.hashCode() + salt.hashCode());
//        return String.valueOf(password.hashCode());
    }


    public void createUser(String login, String password) {
        savedUsers.put(login, new User(login, passwordHashingFunction(password), true));
    }

    public boolean authenticate(String login, String inputPassword) {
        val user = savedUsers.get(login);
        if (user == null) {
            return false;
        }

        return user.isActive() && user.password().equals(passwordHashingFunction(inputPassword));
    }

    public void crackPassword(String login) {
        var user = savedUsers.get(login);

        var start = System.currentTimeMillis();

        System.out.println(passwordHashingFunction(String.valueOf("123784")));

        for (int i = 0; i < 1_000_000; i++) {
            if (user.password().equals(passwordHashingFunction(String.valueOf(i)))) {
                System.out.println("Złamane hasło " + i);
                System.out.println(System.currentTimeMillis() - start);
                return;
            }
        }

    }
}
