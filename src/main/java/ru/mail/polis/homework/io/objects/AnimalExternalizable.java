package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    public AnimalExternalizable(AnimalExternalizable a) {
        this(a.name, a.species, a.admissionDate, a.group, a.disease, a.favouriteFood, a.age, a.length, a.height);
    }

    enum AnimalGroup{
        MAMMAL,
        BIRD,
        FISH,
        REPTILE
    }

    private String name;
    private String species;
    private Date admissionDate;
    private AnimalGroup group;
    private DiseaseExternalizable disease;
    private List<String> favouriteFood;

    private int age;

    private double length;
    private double height;

    public AnimalExternalizable(){

    }

    public AnimalExternalizable(String name, String species, Date admissionDate, AnimalGroup group, DiseaseExternalizable disease, List<String> favouriteFood, int age, double length, double height) {
        this.name = name;
        this.species = species;
        this.admissionDate = admissionDate;
        this.group = group;
        this.disease = disease;
        this.favouriteFood = favouriteFood;
        this.age = age;
        this.length = length;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public AnimalGroup getGroup() {
        return group;
    }

    public int getAge() {
        return age;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", admissionDate=" + admissionDate +
                ", group=" + group +
                ", disease=" + disease +
                ", favouriteFood=" + favouriteFood +
                ", age=" + age +
                ", length=" + length +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return age == animal.age &&
                Double.compare(animal.length, length) == 0 &&
                Double.compare(animal.height, height) == 0 &&
                name.equals(animal.name) &&
                species.equals(animal.species) &&
                admissionDate.equals(animal.admissionDate) &&
                group == animal.group &&
                disease.equals(animal.disease) &&
                favouriteFood.equals(animal.favouriteFood);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(species);
        out.writeObject(admissionDate);
        out.writeObject(group);
        out.writeObject(disease);
        out.writeObject(favouriteFood);
        out.writeInt(age);
        out.writeDouble(length);
        out.writeDouble(height);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        species = in.readUTF();
        admissionDate = (Date) in.readObject();
        group = (AnimalGroup) in.readObject();
        disease = (DiseaseExternalizable) in.readObject();
        favouriteFood = (List<String>) in.readObject();
        age = in.readInt();
        length = in.readDouble();
        height = in.readDouble();
    }
}
