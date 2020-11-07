package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

    public enum NutritionType {
        PLANT, MEAT, BOTH
    }

    private String name;
    private boolean isMale;
    private NutritionType nutritionType;
    private int age;
    private List<String> childNames;
    private Heart heart;

    public AnimalExternalizable(String name, boolean isMale, NutritionType nutritionType, int age, List<String> childNames, Heart heart) {
        this.name = name;
        this.isMale = isMale;
        this.nutritionType = nutritionType;
        this.age = age;
        this.childNames = childNames;
        this.heart = heart;
    }

    public AnimalExternalizable() {
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.name);
        objectOutput.writeBoolean(this.isMale);
        objectOutput.writeObject(this.nutritionType);
        objectOutput.writeInt(this.age);
        objectOutput.writeObject(this.childNames);
        objectOutput.writeObject(this.heart);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.name = (String) objectInput.readObject();
        this.isMale = objectInput.readBoolean();
        this.nutritionType = (NutritionType) objectInput.readObject();
        this.age = objectInput.readInt();
        this.childNames = (List<String>) objectInput.readObject();
        this.heart = (Heart) objectInput.readObject();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setHeart(Heart heart) {
        this.heart = heart;
    }

    public Heart getHeart() {
        return heart;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

    public NutritionType getNutritionType() {
        return nutritionType;
    }

    public List<String> getChildNames() {
        return childNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return isMale == that.isMale &&
                age == that.age &&
                Objects.equals(name, that.name) &&
                nutritionType == that.nutritionType &&
                Objects.equals(childNames, that.childNames) &&
                Objects.equals(heart, that.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isMale, nutritionType, age, childNames, heart);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", nutritionType=" + nutritionType +
                ", age=" + age +
                ", childNames=" + childNames +
                ", heart=" + heart +
                '}';
    }
}
