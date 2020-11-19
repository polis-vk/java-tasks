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
public class AnimalWithMethods implements Serializable{

    private int age;
    private String name;
    private Color color;
    private List<Friend> friends;
    private AnimalWithMethods mother;
    private AnimalWithMethods father;
    private double strong;

    public AnimalWithMethods(int age, String name, Color color, List<Friend> friends, AnimalWithMethods mother, AnimalWithMethods father, double strong) {
        this.age = age;
        this.name = name;
        this.color = color;
        this.friends = friends;
        this.mother = mother;
        this.father = father;
        this.strong = strong;
    }

    public AnimalWithMethods() {}

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        AnimalWithMethods object = (AnimalWithMethods) obj;
        return (age == object.age) &&
                (strong == object.strong) &&
                (color == object.color) &&
                (Objects.equals(friends, object.friends)) &&
                (Objects.equals(name, object.name)) &&
                (Objects.equals(mother, object.mother)) &&
                (Objects.equals(father, object.father));
    }

    public AnimalWithMethods getMother() {
        return mother;
    }

    public void setMother(AnimalWithMethods mother) {
        this.mother = mother;
    }

    public AnimalWithMethods getFather() {
        return father;
    }

    public void setFather(AnimalWithMethods father) {
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

    private void writeObject(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeUTF(color.name());
        out.writeObject(friends);
        out.writeObject(father);
        out.writeObject(mother);
        out.writeDouble(strong);
    }

    private void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        color = Color.valueOf(in.readUTF());
        friends = (List<Friend>) in.readObject();
        father = (AnimalWithMethods) in.readObject();
        mother = (AnimalWithMethods) in.readObject();
        strong = in.readDouble();
    }

    public enum Color{
        WHITE,
        BLACK,
        GREEN,
        YELLOW
    }

    public static  class Friend implements Serializable {
        private String name;
        private int age;
        private Color color;

        public Friend(String name, int age, Color color) {
            this.name = name;
            this.age = age;
            this.color = color;
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

        private void writeObject(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeInt(age);
            out.writeUTF(color.name());
        }

        private void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
            name = in.readUTF();
            age = in.readInt();
            color = Color.valueOf(in.readUTF());
        }
    }
}
