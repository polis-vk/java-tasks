package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private int age;
    private double weight;
    private boolean gender;
    private String name;
    private animalType kindOfAnimal;
    private List<String> food;
    private Colour animalColor;

    public AnimalWithMethods(int a, double w, boolean s, String n, animalType t, List<String> f, Colour c) {
        this.age = a;
        this.weight = w;
        this.gender = s;
        this.name = n;
        this.kindOfAnimal = t;
        this.food = f;
        this.animalColor = c;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", kind of animal='" + kindOfAnimal + '\'' +
                ", age=" + age + '\'' +
                ", gender=" + gender + '\'' +
                ", food=" + food +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnimalWithMethods animal = (AnimalWithMethods) obj;
        return this.name.equals(animal.name) &&
                this.age == animal.age &&
                this.weight == animal.weight &&
                this.gender == animal.gender &&
                this.kindOfAnimal == animal.kindOfAnimal &&
                this.food.equals(animal.food) &&
                Objects.equals(this.animalColor, animal.animalColor);
    }

    public animalType getKindOfAnimal() {
        return kindOfAnimal;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public List<String> getFood() {
        return food;
    }

    public String getName() {
        return name;
    }

    public boolean getGender() {
        return gender;
    }

    public Colour getAnimalColor() {
        return animalColor;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeUTF(kindOfAnimal.toString());
        out.writeBoolean(gender);

        out.writeInt(food.size());
        for (String eat : food) {
            out.writeUTF(eat);
        }

        out.writeInt(animalColor.getMainColor());
        out.writeBoolean(animalColor.getStains());
        out.writeBoolean(animalColor.getStripes());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readDouble();
        String str = in.readUTF();
        kindOfAnimal = animalType.valueOf(str);
        gender = in.readBoolean();

        int listSize = in.readInt();
        food = new ArrayList<String>(listSize);
        for (int j = 0; j < listSize; j++) {
            food.add(in.readUTF());
        }

        int m = in.readInt();
        boolean sa = in.readBoolean();
        boolean st = in.readBoolean();
        animalColor = new Colour(m, sa, st);
    }
}
