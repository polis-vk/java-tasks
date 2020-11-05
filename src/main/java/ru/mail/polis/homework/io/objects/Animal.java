package ru.mail.polis.homework.io.objects;

import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */

public class Animal {
    private String name;
    private int age;
    private Food food;
    public enum Food {
        MEET,
        PLANTS,
        OTHER
    }
    List<String> friends;
    private Size size;
    private boolean isPredator;
    private static class Size{
        double width;
        double height;
        double length;

        public Size(double width, double height, double length) {
            this.width = width;
            this.height = height;
            this.length = length;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        public double getLength() {
            return length;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setLength(double length) {
            this.length = length;
        }
    }

    public Animal(String name, int age, Food food, List<String> friends, Size size, boolean isPredator) {
        this.name = name;
        this.age = age;
        this.food = food;
        this.friends = friends;
        this.size = size;
        this.isPredator = isPredator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isPredator == animal.isPredator &&
                Objects.equals(name, animal.name) &&
                Objects.equals(age, animal.age) &&
                food == animal.food &&
                Objects.equals(friends, animal.friends) &&
                Objects.equals(size, animal.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, food, friends, size, isPredator);
    }
}
