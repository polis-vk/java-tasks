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
    private int age;
    private List<Integer> animalSizes;
    private Color mainColor;
    private Color eyeColor;
    private int numberOfLimbs;
    private Sex sex;
    private Habitat habitat;

    static class Habitat implements Serializable {
        private String placeName;
        private int averageTemperature;
        private int heightAboveSeaLevel;

        Habitat(String placeName, int averageTemperature, int heightAboveSeaLevel) {
            this.placeName = placeName;
            this.averageTemperature = averageTemperature;
            this.heightAboveSeaLevel = heightAboveSeaLevel;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public int getAverageTemperature() {
            return averageTemperature;
        }

        public void setAverageTemperature(int averageTemperature) {
            this.averageTemperature = averageTemperature;
        }

        public int getHeightAboveSeaLevel() {
            return heightAboveSeaLevel;
        }

        public void setHeightAboveSeaLevel(int heightAboveSeaLevel) {
            this.heightAboveSeaLevel = heightAboveSeaLevel;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Habitat habitat = (Habitat) o;
            return averageTemperature == habitat.averageTemperature &&
                    heightAboveSeaLevel == habitat.heightAboveSeaLevel &&
                    placeName.equals(habitat.placeName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(placeName, averageTemperature, heightAboveSeaLevel);
        }

        @Override
        public String toString() {
            return "Habitat{" +
                    "placeName='" + placeName + '\'' +
                    ", averageTemperature=" + averageTemperature +
                    ", heightAboveSeaLevel=" + heightAboveSeaLevel +
                    '}';
        }
    }

    public Animal(AnimalTypeClass typeAnimal,
                  String nickname,
                  int weight,
                  int age,
                  List<Integer> animalSizes,
                  Color mainColor,
                  Color eyeColor,
                  int numberOfLimbs,
                  Sex sex,
                  Habitat habitat) {
        this.typeAnimal = typeAnimal;
        this.nickname = nickname;
        this.weight = weight;
        this.age = age;
        this.animalSizes = animalSizes;
        this.mainColor = mainColor;
        this.eyeColor = eyeColor;
        this.numberOfLimbs = numberOfLimbs;
        this.sex = sex;
        this.habitat = habitat;
    }

    public Animal() {
        this.typeAnimal = AnimalTypeClass.UNKNOWN;
        this.nickname = "";
        this.weight = 0;
        this.age = 0;
        this.animalSizes = new ArrayList<>();
        this.mainColor = Color.UNKNOWN;
        this.eyeColor = Color.UNKNOWN;
        this.numberOfLimbs = 0;
        this.sex = Sex.MALE;
        this.habitat = new Habitat("", 0, 0);
    }

    enum AnimalTypeClass {
        MAMMALS,
        BIRDS,
        FISH,
        REPTILES,
        ARTHROPODS,
        UNKNOWN
    }

    enum Color {
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

    enum Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }

    public AnimalTypeClass getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(AnimalTypeClass typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Integer> getAnimalSizes() {
        return animalSizes;
    }

    public void setAnimalSizes(List<Integer> animalSizes) {
        this.animalSizes = animalSizes;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public int getNumberOfLimbs() {
        return numberOfLimbs;
    }

    public void setNumberOfLimbs(int numberOfLimbs) {
        this.numberOfLimbs = numberOfLimbs;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return weight == animal.weight &&
                age == animal.age &&
                numberOfLimbs == animal.numberOfLimbs &&
                typeAnimal == animal.typeAnimal &&
                nickname.equals(animal.nickname) &&
                animalSizes.equals(animal.animalSizes) &&
                mainColor == animal.mainColor &&
                eyeColor == animal.eyeColor &&
                sex == animal.sex &&
                habitat.equals(animal.habitat);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(typeAnimal, nickname, weight, age, animalSizes, mainColor, eyeColor, numberOfLimbs, sex, habitat);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "typeAnimal=" + typeAnimal +
                ", nickname='" + nickname + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", animalSizes=" + animalSizes +
                ", mainColor=" + mainColor +
                ", eyeColor=" + eyeColor +
                ", numberOfLimbs=" + numberOfLimbs +
                ", sex=" + sex +
                ", habitat=" + habitat +
                '}';
    }
}
