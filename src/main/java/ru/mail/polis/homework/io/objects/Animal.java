package ru.mail.polis.homework.io.objects;


import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
@Getter
public class Animal implements Serializable {

    private int age;
    private String name;
    private Habitat habitat;
    private List<String> food;
    private boolean sexIsMale;
    private double height;
    private Heart heart;


    public enum Habitat {
        WATER,
        LAND,
        AIR
    }

    public Animal(int age, String name, Habitat habitat, List<String> food,
                  boolean sexIsMale, double height, Heart heart) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = sexIsMale;
        this.height = height;
        this.heart = heart;
    }

    public Animal() {

    }

    public Builder createBuilder() {
        return new Animal().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public Builder setAge(int age){
            Animal.this.age = age;
            return this;
        }

        public void setName(String name) {
            Animal.this.name = name;
        }

        public void setHabitat(Habitat habitat) {
            Animal.this.habitat = habitat;
        }

        public void setFood(List<String> food) {
            Animal.this.food = food;
        }
         public void setSex(boolean sexIsMale) {
            Animal.this.sexIsMale = sexIsMale;
         }

         public void setHeight(double height) {
            Animal.this.height = height;
         }

         public void setHeart(Heart heart) {
            Animal.this.heart = heart;
         }

         public Animal build() {
            return Animal.this;
         }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                sexIsMale == animal.sexIsMale &&
                Double.compare(animal.height, height) == 0 &&
                Objects.equals(name, animal.name) &&
                habitat == animal.habitat &&
                Objects.equals(food, animal.food) &&
                Objects.equals(heart, animal.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height, heart);
    }

    @Override
    public String toString() {
        return "Animal{" +
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
