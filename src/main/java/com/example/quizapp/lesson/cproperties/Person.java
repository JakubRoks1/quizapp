package com.example.quizapp.lesson.cproperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@ConfigurationProperties(prefix = "person")
@Validated
// praca domowa stworz wlasny configurationProperties aby mial jak najwiecej technik pokazanych na zajeciach
// wypisz (komentarzem) jakie funkcjonalnosci zostaly zaimplementowane - np. mapowanie z enuma przy użyciu convertera
// wymóg jest taki aby klasa wyjściowa była rekordem (JAVA Record) - public record ABC(....)
public class Person {
    @Length(min = 20)
    private final String imie;
    private final String middleName;
    private final int age;
    private final String address;
    private final Gender gender;
    private final boolean isMarried;
    private final LocalDate birthDate;

    private final Duration duration;
    private final Set<Integer> numbers;
    private final Map<String, Boolean> map;
    @NestedConfigurationProperty
    private final Pet pet;

    private final Fruit fruit;
    @NestedConfigurationProperty
    private final List<Pet> cats;

    @ConstructorBinding
    public Person(String firstName, String middleName, int age, @DefaultValue("default-address") String address,
                  @DefaultValue("M") Gender gender, boolean isMarried, LocalDate birthDate,
                  @DurationUnit(ChronoUnit.HOURS) Duration duration, Set<Integer> numbers, Map<String, Boolean> map, Pet pet, @DefaultValue Fruit fruit, List<Pet> cats) {
        this.map = map;
        this.pet = pet;
        this.fruit = fruit;
        this.cats = cats;
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
        this.numbers = numbers;
    }

    public record Fruit(double price, String name) {};
}
