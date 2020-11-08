package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private animalType type;
    private String name;
    private int age;
    private boolean sex;
    private List<Date> dateOfVaccination;
    private Food fplan;

    public AnimalWithMethods(animalType type, String name, int age, boolean sex, List<Date> dateOfVaccination, Food fplan) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.dateOfVaccination = dateOfVaccination;
        this.fplan = fplan;
    }

    public AnimalWithMethods() {
    }

    public animalType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getSex() {
        return sex;
    }

    public Food getfplan() {
        return fplan;
    }

    public List<Date> getDateOfVaccination() {
        return dateOfVaccination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AnimalWithMethods animal = (AnimalWithMethods) obj;

        return type.equals(animal.type) &&
                name.equals(animal.name) &&
                age == animal.age &&
                sex == animal.sex &&
                dateOfVaccination.equals(animal.dateOfVaccination) &&
                fplan.equals(animal.fplan);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.getType().toString());
        out.writeUTF(this.getName());
        out.writeInt(this.getAge());
        out.writeBoolean(this.getSex());
        out.writeInt(dateOfVaccination.size());
        for (Date date : dateOfVaccination) {
            out.writeUTF(date.toString());
        }
        out.writeUTF(fplan.getFtype().toString());
        out.writeDouble(fplan.getWeight());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        type = animalType.valueOf(in.readUTF());
        name = in.readUTF();
        age = in.readInt();
        sex = in.readBoolean();
        int size = in.readInt();
        dateOfVaccination = new ArrayList<Date>(size);
        for (int i = 0; i < size; i++) {
            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(in.readUTF());
                dateOfVaccination.add(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String f = in.readUTF();
        double w = in.readDouble();
        fplan = new Food(w, Food.foodType.valueOf(f));

    }
}