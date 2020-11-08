package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */

public class AnimalExternalizable implements Externalizable {
    private animalType type;
    private String name;
    private int age;
    private boolean sex;
    private List<Date> dateOfVaccination;
    private Food fplan;

    public AnimalExternalizable(animalType type, String name, int age, boolean sex, List<Date> dateOfVaccination, Food fplan) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.dateOfVaccination = dateOfVaccination;
        this.fplan = fplan;
    }

    public AnimalExternalizable() {
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
        AnimalExternalizable animal = (AnimalExternalizable) obj;

        return type.equals(animal.type) &&
                name.equals(animal.name) &&
                age == animal.age &&
                sex == animal.sex &&
                dateOfVaccination.equals(animal.dateOfVaccination) &&
                fplan.equals(animal.fplan);
    }

    @Override
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

    @Override
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
