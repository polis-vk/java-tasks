package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private boolean isHomePet;
    private boolean isBigAnimal;
    private TypeOfFood typeOfFood;
    private OrganizationSerializable organizationSerializable;

    public AnimalWithMethods(String name, int age, boolean isHomePet, boolean isBigAnimal, TypeOfFood typeOfFood, OrganizationSerializable organizationSerializable) {
        this.name = name;
        this.age = age;
        this.isHomePet = isHomePet;
        this.isBigAnimal = isBigAnimal;
        this.typeOfFood = typeOfFood;
        this.organizationSerializable = organizationSerializable;
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

    public OrganizationSerializable getOrganizationExternalizable() {
        return organizationSerializable;
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

    public void setOrganizationSerializable(OrganizationSerializable organizationSerializable) {
        this.organizationSerializable = organizationSerializable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age && isHomePet == that.isHomePet && isBigAnimal == that.isBigAnimal && Objects.equals(name, that.name) && typeOfFood == that.typeOfFood && Objects.equals(organizationSerializable, that.organizationSerializable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isHomePet, isBigAnimal, typeOfFood, organizationSerializable);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isHomePet=" + isHomePet +
                ", isBigAnimal=" + isBigAnimal +
                ", typeOfFood=" + typeOfFood +
                ", organizationSerializable=" + organizationSerializable +
                '}';
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        OrganizationSerializable organizationSerializable = new OrganizationSerializable("GreenPeece", 634);
        AnimalWithMethods animalWithMethods = new AnimalWithMethods("Kostya", 20, true, true, TypeOfFood.predators, organizationSerializable);


       /* FileOutputStream fileInputStream = new FileOutputStream("test2.txt");
        ObjectOutputStream objectInputStream = new ObjectOutputStream(fileInputStream);

        objectInputStream.writeObject(animalWithMethods);*/

        FileInputStream fileInputStream = new FileInputStream("test2.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


        AnimalWithMethods animalWithMethods2 = (AnimalWithMethods) objectInputStream.readObject();
        System.out.println(animalWithMethods2);

        objectInputStream.close();

    }
}

class OrganizationSerializable implements Serializable {
    private String name;
    private int cntPets;

    public OrganizationSerializable() {
    }

    public OrganizationSerializable(String name, int cntPets) {
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
        OrganizationSerializable that = (OrganizationSerializable) o;
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
}