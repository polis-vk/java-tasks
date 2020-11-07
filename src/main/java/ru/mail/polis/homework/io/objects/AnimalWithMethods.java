package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
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

    public AnimalWithMethods(AnimalTypeClass typeAnimal,
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
        AnimalWithMethods animal = (AnimalWithMethods) o;
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
        return "AnimalWithMethods{" +
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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(typeAnimal);
        out.writeUTF(nickname);
        out.writeInt(weight);
        out.writeInt(age);
        out.writeObject(animalSizes);
        out.writeObject(mainColor);
        out.writeObject(eyeColor);
        out.writeInt(numberOfLimbs);
        out.writeObject(sex);
        out.writeObject(habitat);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        typeAnimal = (AnimalTypeClass) in.readObject();
        nickname = in.readUTF();
        weight = in.readInt();
        age = in.readInt();
        animalSizes = (List<Integer>) in.readObject();
        mainColor = (Color) in.readObject();
        eyeColor = (Color) in.readObject();
        numberOfLimbs = in.readInt();
        sex = (Sex) in.readObject();
        habitat = (Habitat) in.readObject();
    }
}
