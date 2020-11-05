package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods {
    private String name;
    private int age;
    private Food food;

    public AnimalWithMethods() {

    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeInt(sizeFriend);
        for(int i = 0; i < sizeFriend; ++i) {
            out.writeUTF(friends.get(i));
        }
        out.writeBoolean(isPredator);
        out.writeObject(size);
        out.writeObject(food);
    }

    public enum Food {
        MEET,
        PLANTS,
        OTHER
    }
    List<String> friends;
    private int sizeFriend;
    private Size size;
    private boolean isPredator;
    private static class Size{
        double width;
        double height;
        double length;

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
    }

    public AnimalWithMethods(String name, int age, Food food, List<String> friends, Size size, boolean isPredator) {
        this.name = name;
        this.age = age;
        this.food = food;
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

    public void setFriends(List<String> friends) {
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


    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        sizeFriend = in.readInt();
        friends = (List<String>) in.readObject();
        size = (Size) in.readObject();
        isPredator = in.readBoolean();
        food = (Food) in.readObject();
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, age, food, friends, sizeFriend, size, isPredator);
    }
}