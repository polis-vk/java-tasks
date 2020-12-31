package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private Food food;

    public Animal() { }

    public enum Food {
        MEET,
        PLANTS,
        OTHER,
    }



    private int sizeFriend;
    ArrayList<String> friends;
    private Size size;
    private boolean isPredator;
    public static class Size implements Serializable {
        private double width;
        private double height;
        private double length;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Size)) return false;
            Size size = (Size) o;
            return Double.compare(size.width, width) == 0 &&
                    Double.compare(size.height, height) == 0 &&
                    Double.compare(size.length, length) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(width, height, length);
        }

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

        @Override
        public String toString() {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    ", length=" + length +
                    '}';
        }
    }

    public Animal(String name, int age, Food food, int sizeFriend, ArrayList<String> friends, Size size, boolean isPredator) {
        this.name = name;
        this.age = age;
        this.food = food;
        this.sizeFriend = sizeFriend;
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

    public int getSizeFriend() {
        return sizeFriend;
    }

    public void setSizeFriend(int sizeFriend) {
        this.sizeFriend = sizeFriend;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
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

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", food=" + food +
                ", friends=" + friends +
                ", size=" + size +
                ", isPredator=" + isPredator +
                '}';
    }
}

