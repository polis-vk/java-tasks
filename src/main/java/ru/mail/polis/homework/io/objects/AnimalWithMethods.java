package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.*;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods  {
    private String name = "unknown";
    private int age;
    private Habitat habitat = new Habitat();
    private AnimalKind kind = AnimalKind.UNKNOWN;
    private Gender gender = Gender.UNKNOWN;
    private List<String> familyMembersNames = new ArrayList<>();


    private void writeObject(ObjectOutputStream out) throws IOException{
        out.writeObject(kind);
        out.writeObject(habitat);
        out.writeObject(gender);
        out.writeObject(familyMembersNames);
        out.writeInt(age);
        out.writeUTF(name);
    }

    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException{
        kind = (AnimalKind) in.readObject();
        name = in.readUTF();
        age = in.readInt();
        if (age > 200) {
            throw new IllegalArgumentException();
        }
        gender = (Gender) in.readObject();
        habitat = (Habitat) in.readObject();
        familyMembersNames = (List<String>) in.readObject();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public void setKind(AnimalKind kind) {
        this.kind = kind;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public AnimalKind getKind() {
        return kind;
    }

    public Gender getGender() {
        return gender;
    }

    public List<String> getFamilyMembersNames() {
        return familyMembersNames;
    }

    public void setFamilyMembersNames(List<String> family) {
        this.familyMembersNames = family;
    }

    public void addFamilyMembersNames(String... memberName) {
        AnimalWithMethods.this.familyMembersNames.addAll(Arrays.asList(memberName));
    }

    public AnimalWithMethods getRandom(Random random) {
        setAge(random.nextInt(199));
        setGender(Gender.getRandom(random));
        setHabitat(new Habitat(ClimateZone.getRandom(random), Mainland.getRandom(random)));
        setKind(AnimalKind.getRandom(random));
        setName(Utils.getRandomString(random, 10));
        for (int i = 0; i < random.nextInt(20); i++) {
            addFamilyMembersNames(Utils.getRandomString(random, 10));
        }
        return AnimalWithMethods.this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(habitat, that.habitat) &&
                kind == that.kind &&
                gender == that.gender &&
                Objects.equals(familyMembersNames, that.familyMembersNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, habitat, kind, gender, familyMembersNames);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", habitat=" + habitat +
                ", kind=" + kind +
                ", gender=" + gender +
                ", familyMembersNames=" + familyMembersNames +
                '}';
    }
}
