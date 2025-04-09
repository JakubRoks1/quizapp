package com.example.quizapp.cproperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@EqualsAndHashCode
@ToString
@ConfigurationProperties(prefix = "person")
public class Person {
    private final String imie;
    private final String middleName;
    private final int age;
    private final String address;
    private final Gender gender;
    private final boolean isMarried;
    private final LocalDate birthDate;

    private final Duration duration;

    @ConstructorBinding
    public Person(String firstName, String middleName, int age, @DefaultValue("default-address") String address,
                  @DefaultValue("M") Gender gender, boolean isMarried, LocalDate birthDate,
                  @DurationUnit(ChronoUnit.HOURS) Duration duration) {
        System.out.println("konstruktor persona");
        this.imie = firstName;
        if (middleName == null) {
            throw new RuntimeException("middle name cannot be null");
        }
        this.middleName = middleName;
        this.age = age;
        this.duration = duration;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.isMarried = isMarried;
    }
}
