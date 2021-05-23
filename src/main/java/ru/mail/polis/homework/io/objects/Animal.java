package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    int age;
    int energy;

    String kind;
    Classis classis;

    Part leftPaw = new Part("right leg", 100, 1);
    Part rightPaw = new Part("left leg", 100, 1);
    Part head = new Part("head", 80, 100);

    class Part {
        String partName;
        double healthPoint;
        int energyPerUse;

        public Part(String partName, double healthPoint, int energyPerUse) {
            this.partName = partName;
            this.healthPoint = healthPoint;
            this.energyPerUse = energyPerUse;
        }

        void usePart() {
            energy -= energyPerUse;
        }
    }

    public enum Classis {
        MAMMAL,
        BIRD,
        REPTILE,
        FISH,
        AMPHIBIAN,
        INVERTEBRATE;
    }
}
