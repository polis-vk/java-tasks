package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private boolean isHomePet;
    private boolean isBigAnimal;
    private TypeOfFood typeOfFood;
    private OrganizationExternalizable organizationExternalizable;

    public AnimalExternalizable() { }

    public AnimalExternalizable(String name, int age, boolean isHomePet, boolean isBigAnimal, TypeOfFood typeOfFood, OrganizationExternalizable organizationExternalizable) {
        this.name = name;
        this.age = age;
        this.isHomePet = isHomePet;
        this.isBigAnimal = isBigAnimal;
        this.typeOfFood = typeOfFood;
        this.organizationExternalizable = organizationExternalizable;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isHomePet() {
        return isHomePet;
    }

    public boolean isBigAnimal() {
        return isBigAnimal;
    }

    public TypeOfFood getTypeOfFood() {
        return typeOfFood;
    }

    public OrganizationExternalizable getOrganizationExternalizable() {
        return organizationExternalizable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHomePet(boolean homePet) {
        isHomePet = homePet;
    }

    public void setBigAnimal(boolean bigAnimal) {
        isBigAnimal = bigAnimal;
    }

    public void setTypeOfFood(TypeOfFood typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public void setOrganizationExternalizable(OrganizationExternalizable organizationExternalizable) {
        this.organizationExternalizable = organizationExternalizable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age && isHomePet == that.isHomePet && isBigAnimal == that.isBigAnimal && Objects.equals(name, that.name) && typeOfFood == that.typeOfFood && Objects.equals(organizationExternalizable, that.organizationExternalizable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isHomePet, isBigAnimal, typeOfFood, organizationExternalizable);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isHomePet=" + isHomePet +
                ", isBigAnimal=" + isBigAnimal +
                ", typeOfFood=" + typeOfFood +
                ", organizationExternalizable=" + organizationExternalizable +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
        out.flush();
        out.writeByte(isHomePet ? 1 : 0);
        out.flush();

        out.writeByte(isBigAnimal ? 1 : 0);
        out.flush();

        out.writeInt((typeOfFood == null) ? -1 : typeOfFood.ordinal());
        out.flush();

        out.writeObject(organizationExternalizable);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
        isHomePet = in.readByte() == 1;
        isBigAnimal = in.readByte() == 1;
        int valueTypeOfFood = in.readInt();
        typeOfFood = (valueTypeOfFood != -1) ? TypeOfFood.values()[valueTypeOfFood] : null;
        organizationExternalizable = (OrganizationExternalizable) in.readObject();
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
////        OrganizationExternalizable organizationExternalizable = new OrganizationExternalizable("GreenPeece", 634);
////        AnimalExternalizable animalExternalizable = new AnimalExternalizable("Kostya", 20, true, true, TypeOfFood.predators, organizationExternalizable);
//
//
////        FileOutputStream fileInputStream = new FileOutputStream("test.txt");
////        ObjectOutputStream objectInputStream = new ObjectOutputStream(fileInputStream);
////
////        objectInputStream.writeObject(animalExternalizable);
//
//        FileInputStream fileInputStream = new FileInputStream("test.txt");
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//
//        AnimalExternalizable animalExternalizable2 = (AnimalExternalizable) objectInputStream.readObject();
//        System.out.println(animalExternalizable2);
//
//        objectInputStream.close();
//
//    }
}

class OrganizationExternalizable implements Externalizable {
    private String name;
    private int cntPets;

    public OrganizationExternalizable() {
    }

    public OrganizationExternalizable(String name, int cntPets) {
        this.name = name;
        this.cntPets = cntPets;
    }

    public String getName() {
        return name;
    }

    public int getCntPets() {
        return cntPets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCntPets(int cntPets) {
        this.cntPets = cntPets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationExternalizable that = (OrganizationExternalizable) o;
        return cntPets == that.cntPets && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cntPets);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", cntPets=" + cntPets +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(cntPets);
        out.flush();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        cntPets = in.readInt();
    }
}