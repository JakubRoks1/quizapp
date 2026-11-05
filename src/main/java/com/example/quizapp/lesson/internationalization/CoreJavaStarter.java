package com.example.quizapp.lesson.internationalization;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class CoreJavaStarter {

    public static void main(String[] args) {
        Locale pl = Locale.forLanguageTag("pl-PL");
        Locale de = Locale.forLanguageTag("de-DE");
        Locale us = Locale.US;

        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        var now = LocalDate.of(2026, 2, 23);
        System.out.println(now.format(dtf.withLocale(de)));

//        System.out.println(Arrays.toString(NumberFormat.getAvailableLocales()));

        System.out.println(NumberFormat.getCurrencyInstance(de).format(23.54634));

    }
}
