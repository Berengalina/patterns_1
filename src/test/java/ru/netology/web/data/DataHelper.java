package ru.netology.web.data;

import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataHelper {
    private DataHelper() {
    }

    public static String generateByFakerName(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return new String(
                faker.name().fullName().replace('ё', 'е')
        );
    }


    public static String generateByFakerPhone(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return new String(
                faker.phoneNumber().phoneNumber()
        );
    }

    public static String generateByFakerDigit() {
        Faker faker = new Faker();
        return new String(
                faker.number().digit()
        );
    }


    public static String generateByFakerNameForeign(String locale) {
        Faker faker = new Faker(new Locale("es"));
        return new String(
                faker.name().fullName()
        );
    }

    public static String generateFirstDateOfMeeting() {
        String firstDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return firstDate;
    }


    public static String generateSecondDateOfMeeting() {
        String secondDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return secondDate;
    }

    public static String setNameWithSymbols() {
        String name = "Подъёмова Любовь-Майя";
        return name;

    }

    public static String generateCity() {
        String[] adminCities = new String[]{"Москва", "Санкт-Петербург", "Калуга", "Владимир", "Краснодар", "Майкоп", "Уфа", "Пермь",
                "Владивосток", "Рязань", "Самара", "Курск", "Брянск", "Казань", "Хабаровск", "Волгоград", "Иваново", "Вологда",
                "Чита", "Барнаул", "Иркутск", "Якутск", "Улан-Удэ", "Петрозаводск", "Липецк"};
        Random generator = new Random();
        int randomIndex = generator.nextInt(adminCities.length);
        return adminCities[randomIndex];
    }

}

