package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private Animal.Food food;

    public AnimalWithMethods(String name, int age, Animal.Food food, int sizeFriend, ArrayList<String> friends, Size size, Boolean isPredator) {
        this.name = name;
        this.age = age;
        this.food = food;
        this.sizeFriend = sizeFriend;
        this.friends = friends;
        this.size = size;
        this.isPredator = isPredator;
    }

    private int sizeFriend;
    ArrayList<String> friends;
    private Size size;
    private boolean isPredator;

    public static class Size implements Serializable {
        private double width;
        private double height;
        private double length;

        public Size(double width, double height, double length) {
            this.width = width;
            this.height = height;
            this.length = length;
        }

        public Size() {
        }

        @Override
        public String toString() {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    ", length=" + length +
                    '}';
        }

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

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeDouble(width);
            out.writeDouble(height);
            out.writeDouble(length);
        }


        private void readObject(ObjectInputStream in) throws IOException {
            width = in.readDouble();
            height = in.readDouble();
            length = in.readDouble();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
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
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", food=" + food +
                ", friends=" + friends +
                ", size=" + size +
                ", isPredator=" + isPredator +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeObject(food);
        out.writeObject(friends);
        size.writeObject(out);
        out.writeBoolean(isPredator);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        food = (Animal.Food) in.readObject();
        friends = (ArrayList<String>) in.readObject();
        size = new Size();
        size.readObject(in);
        isPredator = in.readBoolean();
    }
}
