package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class Part implements Serializable {

    private final String partName;
    private int healthPoint;
    private Color color;

    private static final String[] NAMES = new String[] {"hand", "paw", "head", "leg", "tentacle"};

    public Part(String partName) {
        this.partName = partName;
    }

    public Part(String partName, int healthPoint, Color color) {
        this.partName = partName;
        this.healthPoint = healthPoint;
        this.color = color;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPartName() {
        return partName;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public Color getColor() {
        return color;
    }

    public static List<Part> getRandomParts(int size) {
        List<Part> parts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            parts.add(getRandomPart());
        }
        return parts;
    }

    public static Part getRandomPart() {
        Random random = new Random();
        return new Part(
                NAMES[random.nextInt(NAMES.length)],
                random.nextInt(100),
                Color.getRandomColor()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return healthPoint == part.healthPoint && Objects.equals(partName, part.partName) && color == part.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partName, healthPoint, color);
    }
}
