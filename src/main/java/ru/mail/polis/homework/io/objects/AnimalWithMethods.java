package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods extends Animal implements Serializable {

    public AnimalWithMethods(int age, String name, Habitat habitat, List<String> food,
                             boolean gender, double height, Heart heart) {
        super(age, name, habitat, food, gender, height, heart);
    }

}
