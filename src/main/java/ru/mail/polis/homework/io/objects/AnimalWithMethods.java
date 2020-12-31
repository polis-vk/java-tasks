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

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeDouble(width);
            out.writeDouble(height);
            out.writeDouble(length);
        }


        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            width = in.readDouble();
            height = in.readDouble();
            length = in.readDouble();
        }
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

    public Animal.Food getFood() {
        return food;
    }

    public void setFood(Animal.Food food) {
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

    private void writeObject(ObjectOutputStream out) throws  IOException, ClassNotFoundException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeObject(food);
        out.writeObject(friends);
        size.writeObject(out);
        out.writeBoolean(isPredator);
    }

    private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        food = (Animal.Food) in.readObject();
        friends = (ArrayList<String>) in.readObject();
        size.readObject(in);
        isPredator = in.readBoolean();
    }
}
