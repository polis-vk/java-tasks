package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    protected int age;
    protected String name;
    protected Habitat habitat;
    protected List<String> food;
    protected boolean sexIsMale;
    protected double height;
    protected Heart heart;

    public enum Habitat {
        WATER,
        LAND,
        AIR
    }

    public AnimalWithMethods(int age, String name, Habitat habitat, List<String> food,
                  boolean gender, double height, Heart heart) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = gender;
        this.height = height;
        this.heart = heart;
    }

    public AnimalWithMethods() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age &&
                sexIsMale == that.sexIsMale &&
                Double.compare(that.height, height) == 0 &&
                Objects.equals(name, that.name) &&
                habitat == that.habitat &&
                Objects.equals(food, that.food) &&
                Objects.equals(heart, that.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height, heart);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", habitat=" + habitat +
                ", food=" + food +
                ", sexIsMale=" + sexIsMale +
                ", height=" + height +
                ", heart=" + heart +
                '}';
    }
}
