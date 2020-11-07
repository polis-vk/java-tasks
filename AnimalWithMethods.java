package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    public enum NutritionType {
        PLANT, MEAT, BOTH
    }

    private final String name;
    private final boolean isMale;
    private final NutritionType nutritionType;
    private int age;
    private List<String> childNames;
    private Heart heart;

    public AnimalWithMethods(String name, boolean isMale, NutritionType nutritionType, int age, List<String> childNames, Heart heart) {
        this.name = name;
        this.isMale = isMale;
        this.nutritionType = nutritionType;
        this.age = age;
        this.childNames = childNames;
        this.heart = heart;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public List<String> getChildNames() {
        return childNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
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
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", nutritionType=" + nutritionType +
                ", age=" + age +
                ", childNames=" + childNames +
                ", heart=" + heart +
                '}';
    }
}
