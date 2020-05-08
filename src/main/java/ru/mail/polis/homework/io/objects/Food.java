package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Food implements Serializable {
    public final String name;
    public final int calorie;

    public Food(String name, int calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return calorie == food.calorie &&
                Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calorie);
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                '}';
    }
}
