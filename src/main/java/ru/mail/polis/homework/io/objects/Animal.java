package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми самописными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private int legs;
    private boolean isDomesticated;
    private boolean isWarmBlooded;
    private String name;
    private Diet diet;
    private Person discoveredBy;
}

class Person implements Serializable {
    private String name;
    private String shortBio;
    private LocalDate born;
    private LocalDate died;
}

enum Diet {
    CARNIVORE,
    OMNIVORE,
    HERBIVORE
}