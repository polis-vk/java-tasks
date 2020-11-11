package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    public AnimalWithMethods(AnimalWithMethods a) {
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
    private DiseaseWithMethods disease;
    private List<String> favouriteFood;

    private int age;

    private double length;
    private double height;

    public AnimalWithMethods(){

    }

    public AnimalWithMethods(String name, String species, Date admissionDate, AnimalGroup group, DiseaseWithMethods disease, List<String> favouriteFood, int age, double length, double height) {
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
        return "AnimalWithMethods{" +
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
        AnimalWithMethods animal = (AnimalWithMethods) o;
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

    private void writeObject(ObjectOutputStream obj) throws IOException {
        obj.writeUTF(name);
        obj.writeUTF(species);
        obj.writeObject(admissionDate);
        obj.writeObject(group);
        obj.writeObject(disease);
        obj.writeObject(favouriteFood);
        obj.writeInt(age);
        obj.writeDouble(length);
        obj.writeDouble(height);
    }

    private void readObject(ObjectInputStream obj) throws IOException, ClassNotFoundException {
        name = obj.readUTF();
        species = obj.readUTF();
        admissionDate = (Date) obj.readObject();
        group = (AnimalGroup) obj.readObject();
        disease = (DiseaseWithMethods) obj.readObject();
        favouriteFood = (List<String>) obj.readObject();
        age = obj.readInt();
        length = obj.readDouble();
        height = obj.readDouble();
    }
}
