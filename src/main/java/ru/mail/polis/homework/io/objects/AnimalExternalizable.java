package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    public AnimalExternalizable() {
    }

    private String name;
    private int age;
    private Animal.Food food;

    public enum Food {
        MEET,
        PLANTS,
        OTHER,
    }

    private int sizeFriend;
    ArrayList<String> friends;
    private Size size;
    private boolean isPredator;

    public static class Size implements Externalizable {
        private double width;
        private double height;
        private double length;

        public Size(double width, double height, double length) {
            this.width = width;
            this.height = height;
            this.length = length;
        }

        public Size() { }

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

        @Override
        public String toString() {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    ", length=" + length +
                    '}';
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeDouble(width);
            out.writeDouble(height);
            out.writeDouble(length);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            width = in.readDouble();
            height = in.readDouble();
            length = in.readDouble();
        }
    }

    public AnimalExternalizable(String name, int age, Animal.Food food, int sizeFriend, ArrayList<String> friends, Size size, boolean isPredator) {
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
        AnimalExternalizable animal = (AnimalExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeInt(sizeFriend);
        out.writeObject(friends);
        size.writeExternal(out);
        out.writeBoolean(isPredator);
        out.writeObject(food);
    }


    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        setAge(in.readInt());
        setSizeFriend(in.readInt());
        setFriends((ArrayList<String>) in.readObject());
        size = new Size();
        size.readExternal(in);
        setPredator(in.readBoolean());
        setFood((Animal.Food) in.readObject());
    }

}
