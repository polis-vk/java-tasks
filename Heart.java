package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Heart implements Serializable {
    private double weight;
    private double power;

    public Heart(double weight, double power) {
        this.weight = weight;
        this.power = power;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heart heart = (Heart) o;
        return Double.compare(heart.weight, weight) == 0 &&
                Double.compare(heart.power, power) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, power);
    }

    @Override
    public String toString() {
        return "Heart{" +
                "weight=" + weight +
                ", power=" + power +
                '}';
    }
}
