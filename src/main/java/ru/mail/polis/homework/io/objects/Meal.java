package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public class Meal implements Serializable {
    public Meal(String favouriteMeal, LocalDateTime lastMeal, Set<String> allergicTo) {
        this.favouriteMeal = favouriteMeal;
        this.lastMeal = lastMeal;
        this.allergicTo = allergicTo;
    }
    public String favouriteMeal;
    public LocalDateTime lastMeal;
    public Set<String> allergicTo;

    @Override
    public String toString() {
        return "Meal{" +
                "favouriteMeal='" + favouriteMeal + '\'' +
                ", lastMeal=" + lastMeal +
                ", allergicTo=" + allergicTo +
                '}';
    }
}