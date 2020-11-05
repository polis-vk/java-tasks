package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private AnimalTypeClass typeAnimal;
    private String nickname;
    private int weight;
    private List<Integer> animalSizes;
    private Color mainColor;
    private Color eyeColor;
    private int numberOfLimbs;
    private Sex sex;

    public Animal(AnimalTypeClass typeAnimal,
                  String nickname,
                  int weight,
                  List<Integer> animalSizes,
                  Color mainColor,
                  Color eyeColor,
                  int numberOfLimbs,
                  Sex sex) {
        this.typeAnimal = typeAnimal;
        this.nickname = nickname;
        this.weight = weight;
        this.animalSizes = animalSizes;
        this.mainColor = mainColor;
        this.eyeColor = eyeColor;
        this.numberOfLimbs = numberOfLimbs;
        this.sex = sex;
    }

    public Animal() {
        this.sex = Sex.MALE;
        this.typeAnimal = AnimalTypeClass.UNKNOWN;
        this.nickname = "";
        this.weight = 0;
        this.animalSizes = new ArrayList<>();
        this.mainColor = Color.UNKNOWN;
        this.eyeColor = Color.UNKNOWN;
        this.numberOfLimbs = 0;
    }

    enum AnimalTypeClass implements Serializable {
        MAMMALS,
        BIRDS,
        FISH,
        REPTILES,
        ARTHROPODS,
        UNKNOWN
    }

    enum Color implements Serializable {
        RED,
        GRAY,
        WHITE,
        BLACK,
        GREEN,
        YELLOW,
        PINK,
        ORANGE,
        BROWN,
        BLUE,
        UNKNOWN
    }

    enum Sex implements Serializable {
        MALE,
        FEMALE,
        UNKNOWN
    }

    public AnimalTypeClass getTypeAnimal() {
        return typeAnimal;
    }

    public String getNickname() {
        return nickname;
    }

    public int getWeight() {
        return weight;
    }

    public List<Integer> getAnimalSizes() {
        return animalSizes;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public int getNumberOfLimbs() {
        return numberOfLimbs;
    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return weight == animal.weight &&
                numberOfLimbs == animal.numberOfLimbs &&
                typeAnimal == animal.typeAnimal &&
                nickname.equals(animal.nickname) &&
                animalSizes.equals(animal.animalSizes) &&
                mainColor == animal.mainColor &&
                eyeColor == animal.eyeColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeAnimal, nickname, weight, animalSizes, mainColor, eyeColor, numberOfLimbs);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "typeAnimal=" + typeAnimal +
                ", nickname='" + nickname + '\'' +
                ", weight=" + weight +
                ", animalSizes=" + animalSizes +
                ", mainColor=" + mainColor +
                ", eyeColor=" + eyeColor +
                ", numberOfLimbs=" + numberOfLimbs +
                ", sex=" + sex +
                "}\n";
    }

    public void setTypeAnimal(AnimalTypeClass typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAnimalSizes(List<Integer> animalSizes) {
        this.animalSizes = animalSizes;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setNumberOfLimbs(int numberOfLimbs) {
        this.numberOfLimbs = numberOfLimbs;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
