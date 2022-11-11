package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Meal implements Serializable {
    private String favouriteMeal;
    private LocalDateTime lastMeal;
    private int mealsPerDay;

    public Meal(String favouriteMeal, LocalDateTime lastMeal, int mealsPerDay) {
        this.favouriteMeal = favouriteMeal;
        this.lastMeal = lastMeal;
        this.mealsPerDay = mealsPerDay;
    }

    public String getFavouriteMeal() {
        return favouriteMeal;
    }

    public LocalDateTime getLastMeal() {
        return lastMeal;
    }

    public int getMealsPerDay() {
        return mealsPerDay;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "favouriteMeal='" + favouriteMeal + '\'' +
                ", lastMeal=" + lastMeal +
                ", mealsPerDay=" + mealsPerDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meal meal = (Meal) o;
        return mealsPerDay == meal.mealsPerDay && Objects.equals(favouriteMeal, meal.favouriteMeal) && Objects.equals(lastMeal, meal.lastMeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favouriteMeal, lastMeal, mealsPerDay);
    }
}