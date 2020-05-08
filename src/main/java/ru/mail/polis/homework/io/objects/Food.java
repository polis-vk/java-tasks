package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Food implements Serializable {
    public final String foodType;

    public Food(String foodType) {
        this.foodType = foodType;
    }
}
