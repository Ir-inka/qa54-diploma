package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    public static String approvedCardNumber = "4444444444444441";                       // Карта Approved
    public static String declinedCardNumber = "4444444444444442";                       // Карта Declined
    public static String shortCardNumber = "1234 5678 9123 456";                        // Карта меньше 16
    public static String zeroCard = "0000 0000 0000 0000";                              // Карта нули
    public static String emptyCard = "";                                                // Карта пусто
    public static String invalidMonth = "5";                                            // Месяц одно число
    public static String falseMonth = "13";                                             // Месяц больше диапазона
    public static String zeroMonth = "00";                                              // Месяц нули
    public static String emptyMonth = "";                                               // Месяц пусто
    public static String invalidYear = "29";                                            // Год больше диапазона
    public static String falseYear = "5";                                               // Год одна цифра
    public static String zeroYear = "00";                                               // Год нули
    public static String emptyYear = "";                                                // Год пусто
    public static String invalidOwnerOneWord = "Ivan";                                   // Владелец одно слово
    public static String invalidOwnerThreeWord = "Ivanov Ivan Ivanovich";                // Владелец три слова
    public static String charactersOwner = "!»№;%:?";                                     // Владелец символы
    public static String figuresOwner = "123456 123456";                                   // Владелец цифры
    public static String zeroOwner = "0000000 000000";                                     // Владелец нули
    public static String emptyOwner = "";                                                  // Владелец пусто
    public static String zeroCVC = "000";                                                  // CVC нули
    public static String emptyCvc = "";                                                    // CVC пусто

    public static Faker faker = new Faker(new Locale("en"));
    public static Faker fakerRu = new Faker(new Locale("ru"));

    public static String getStatusApprovedCard() {                                                  // Одобрено
        return "APPROVED";
    }

    public static String getStatusDeclinedCard() {                                                  // Отклонено
        return "DECLINED";
    }

    public static String getRandomCard() {                                                          // Рандомный номер карты

        return faker.numerify("#### #### #### ####");
    }

    public static String getMonth() {                                                                 // текущий месяц
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth() {                                                           // Месяц - 1 от текущего
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear() {                                                                       // текущий год
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getNextFromCurrentYear() {                                                           // Следующий от текущего года
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getFrontierYear() {                                                           // Максимум от текущего +5 (без ТЗ,опытным путём)
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getPreviousYear() {                                                           // Предыдущий год
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getRandomValidOwner() {                                                  // Рандомное имя на латинице
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        return name + " " + surname;
    }

    public static String getRandomValidOwnerRu() {                                                   // Рандомное имя на кириллице
        String name = fakerRu.name().firstName();
        String surname = fakerRu.name().lastName();
        return name + " " + surname;
    }

    public static String getRandomValidCvc() {                                                      // рандомный код CVC
        return faker.numerify("###");
    }                     // Рандомное CVC

    public static String getOneCVC() {                                                                 // код CVC 1 число
        return faker.numerify("#");
    }                               // CVC одно число

    public static String getTwoCVC() {                                                               // код CVC два числа
        return faker.numerify("##");
    }                              // CVC два числа

}