package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable  {

    private int age;
    private String name;
    private Color color;
    private List<Friend> friends;
    private AnimalExternalizable mother;
    private AnimalExternalizable father;
    private double strong;

    public AnimalExternalizable(int age, String name, Color color, List<Friend> friends, AnimalExternalizable mother, AnimalExternalizable father, double strong) {
        this.age = age;
        this.name = name;
        this.color = color;
        this.friends = friends;
        this.mother = mother;
        this.father = father;
        this.strong = strong;
    }

    public AnimalExternalizable() {}

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        AnimalExternalizable object = (AnimalExternalizable) obj;
        return (age == object.age) &&
                (strong == object.strong) &&
                (color == object.color) &&
                (Objects.equals(friends, object.friends)) &&
                (Objects.equals(name, object.name)) &&
                (Objects.equals(mother, object.mother)) &&
                (Objects.equals(father, object.father));
    }

    public AnimalExternalizable getMother() {
        return mother;
    }

    public void setMother(AnimalExternalizable mother) {
        this.mother = mother;
    }

    public AnimalExternalizable getFather() {
        return father;
    }

    public void setFather(AnimalExternalizable father) {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeUTF(color.name());
        out.writeInt(friends.size());
        for (Friend friend : friends) {
                out.writeObject(friend);
        }
        out.writeObject(father);
        out.writeObject(mother);
        out.writeDouble(strong);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        color = Color.valueOf(in.readUTF());
        int size = in.readInt();
        friends = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            friends.add((Friend) in.readObject());
        }
        father = (AnimalExternalizable) in.readObject();
        mother = (AnimalExternalizable) in.readObject();
        strong = in.readDouble();
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

    public enum Color{
        WHITE,
        BLACK,
        GREEN,
        YELLOW
    }

    public static  class Friend implements Externalizable {
        private String name;
        private int age;
        private Color color;

        public Friend(String name, int age, Color color) {
            this.name = name;
            this.age = age;
            this.color = color;
        }

        public Friend() {
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

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeInt(age);
            out.writeUTF(color.name());
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = in.readUTF();
            age = in.readInt();
            color = Color.valueOf(color.name());
        }
    }
}
