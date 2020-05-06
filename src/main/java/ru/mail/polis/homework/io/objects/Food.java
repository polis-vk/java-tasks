package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Food implements Serializable {
    public final String name;
    public final int calorie;

    public Food(String name, int calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                '}';
    }
}
