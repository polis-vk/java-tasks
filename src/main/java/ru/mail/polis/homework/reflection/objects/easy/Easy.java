package ru.mail.polis.homework.reflection.objects.easy;

import ru.mail.polis.homework.reflection.ReflectionToStringHelper;

public class Easy {
    private final int age;
    private final String name;
    private final double weight;

    public Easy(int age, String name, double weight) {
        this.age = age;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return ReflectionToStringHelper.reflectiveToString(this);
    }
}
