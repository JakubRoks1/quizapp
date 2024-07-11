package com.example.quizapp.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "custom")
public final class Custom {
    private final String my1;
    private final boolean flag;
    private final int number;
    private final Boolean isFinal;
    private final LocalDate now;
    @DurationUnit(ChronoUnit.SECONDS)
    private final Duration duration;
    private final Class<?> clz;
    private final Inside ins;
    @NestedConfigurationProperty
    private final Outside out;
    private final List<String> list;
    private final List<Inside> list2;
    private final Map<String, Integer> map;
    private final Integer def;

    public Custom(String my1, boolean flag, int number, Boolean isFinal, LocalDate now, Duration duration, Class<?> clz, Inside ins, Outside out, List<String> list, List<Inside> list2, Map<String, Integer> map, @DefaultValue("200") Integer def) {
        this.my1 = my1;
        this.flag = flag;
        this.number = number;
        this.isFinal = isFinal;
        this.now = now;
        this.duration = duration;
        this.clz = clz;
        this.ins = ins;
        this.out = out;
        this.list = list;
        this.list2 = list2;
        this.map = map;
        this.def = def;
    }

    @Data
    public static class Inside {
        private final int a;
        private final int b;
    }


}
