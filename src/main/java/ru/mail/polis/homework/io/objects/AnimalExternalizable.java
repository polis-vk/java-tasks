package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
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

    public AnimalExternalizable(AnimalTypeClass typeAnimal,
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

    public AnimalExternalizable() {
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
        AnimalExternalizable animal = (AnimalExternalizable) o;
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
        return "AnimalExternalizable{" +
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
    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
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

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws IOException            if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
