package ru.mail.polis.homework.reflection.objects.medium;

import ru.mail.polis.homework.reflection.ReflectionToStringHelper;
import ru.mail.polis.homework.reflection.SkipField;

public class Medium {
    public static final int DEFAULT_AGE = 25;
    public static final String DEFAULT_NAME = "John Doe";
    public static final double DEFAULT_WEIGHT = 65.0;

    @SkipField
    private final double weight;
    private final String name;
    private final int age;

    public Medium() {
        this(DEFAULT_AGE, DEFAULT_NAME, DEFAULT_WEIGHT);
    }

    public Medium(int age, String name, double weight) {
        this.age = age;
        this.name = name;
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return ReflectionToStringHelper.reflectiveToString(this);
    }
}
