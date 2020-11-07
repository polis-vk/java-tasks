package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */



public class Animal implements Serializable {
    public enum NutritionType {
        PLANT, MEAT, BOTH
    }

    private final String name;
    private final boolean isMale;
    private final NutritionType nutritionType;
    private int age;
    private List<String> childNames;
    private Heart heart;

    public Animal(String name, boolean isMale, NutritionType nutritionType, int age, List<String> childNames, Heart heart) {
        this.name = name;
        this.isMale = isMale;
        this.nutritionType = nutritionType;
        this.age = age;
        this.childNames = childNames;
        this.heart = heart;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setHeart(Heart heart) {
        this.heart = heart;
    }

    public Heart getHeart() {
        return heart;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

    public NutritionType getNutritionType() {
        return nutritionType;
    }

    public List<String> getChildNames() {
        return childNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isMale == animal.isMale &&
                age == animal.age &&
                Objects.equals(name, animal.name) &&
                nutritionType == animal.nutritionType &&
                Objects.equals(childNames, animal.childNames) &&
                Objects.equals(heart, animal.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isMale, nutritionType, age, childNames, heart);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", nutritionType=" + nutritionType +
                ", age=" + age +
                ", childNames=" + childNames +
                ", heart=" + heart +
                '}';
    }
}
