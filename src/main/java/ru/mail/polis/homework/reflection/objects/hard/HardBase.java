package ru.mail.polis.homework.reflection.objects.hard;

public class HardBase {
    public static final int DEFAULT_AGE = 25;

    private final int age;

    public HardBase() {
        this(DEFAULT_AGE);
    }

    public HardBase(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
