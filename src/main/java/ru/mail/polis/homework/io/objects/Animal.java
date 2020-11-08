package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    public enum Color{
        WHITE,
        BLACK,
        GREEN,
        YELLOW
    }
    private int age;
    private String name;
    private Color color;
    private List<Friend> friends;
    private Animal mother;
    private Animal father;
    private double strong;

    public Animal(int age, String name, Color color, List<Friend> friends, Animal mother, Animal father, double strong) {
        this.age = age;
        this.name = name;
        this.color = color;
        this.friends = friends;
        this.mother = mother;
        this.father = father;
        this.strong = strong;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Animal object = (Animal) obj;
        return (age == object.age) &&
                (strong == object.strong) &&
                (color == object.color) &&
                (Objects.equals(friends, object.friends)) &&
                (Objects.equals(name, object.name)) &&
                (Objects.equals(mother, object.mother)) &&
                (Objects.equals(father, object.father));
    }

    public Animal getMother() {
        return mother;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

    public Animal getFather() {
        return father;
    }

    public void setFather(Animal father) {
        this.father = father;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public double getStrong() {
        return strong;
    }

    public void setStrong(double strong) {
        this.strong = strong;
    }

    @Override
    public int hashCode(){
        return Objects.hash(age, name, color, friends, strong, mother, father);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", friends=" + friends +
                ", mother=" + mother +
                ", father=" + father +
                ", strong=" + strong +
                '}';
    }

    public static  class Friend implements Serializable{
        private String name;
        private int age;
        private Color color;

        public Friend(String name, int age, Color color) {
            this.name = name;
            this.age = age;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Friend{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", color=" + color +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, color);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if(obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Friend o = (Friend) obj;
            return age == o.age &&
                    Objects.equals(name, o.name) &&
                    Objects.equals(color, o.color);
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

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}

