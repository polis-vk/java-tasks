package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Date;

public class Food implements Serializable {
    private final String title;
    private final double weight; // kilograms
    private final Date date;

    public Food(String title, double weight, Date date) {
        this.title = title;
        this.weight = weight;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public double getWeight() {
        return weight;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Food food = (Food) obj;
        return this.title.equals(food.title)
          && this.weight == food.weight
          && this.date.equals(food.date);
    }

    @Override
    public String toString() {
        return "Food{" +
          "title='" + title + '\'' +
          ", weight=" + weight +
          ", date=" + date +
          '}';
    }
}
