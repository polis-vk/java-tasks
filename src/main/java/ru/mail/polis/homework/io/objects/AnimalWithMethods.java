package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private boolean isPet;
    private boolean isWild;
    private Integer legsCount;
    private String name;
    private Double age;
    private MoveType moveType;
    private Byte id;
    private LivingEnvironment livingEnvironment;

    public AnimalWithMethods() {

    }

    public AnimalWithMethods(boolean isPet, boolean isWild, Integer legsCount, String name, Double age,
                             MoveType moveType, Byte id, LivingEnvironment livingEnvironment) {
        this.isPet = isPet;
        this.isWild = isWild;
        this.legsCount = legsCount;
        this.name = name;
        this.age = age;
        this.moveType = moveType;
        this.id = id;
        this.livingEnvironment = livingEnvironment;
    }

    public static class LivingEnvironment implements Serializable {
        private double temperature;
        private Weather weather;

        public LivingEnvironment(double temperature, Weather weather) {
            this.temperature = temperature;
            this.weather = weather;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LivingEnvironment that = (LivingEnvironment) o;
            return Double.compare(that.temperature, temperature) == 0 && weather == that.weather;
        }

        @Override
        public int hashCode() {
            return Objects.hash(temperature, weather);
        }

        @Override
        public String toString() {
            return "LivingEnvironment{" +
                    "temperature=" + temperature +
                    ", weather=" + weather +
                    '}';
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeDouble(temperature);
            out.writeByte(assertNullGetBit(weather));
            if (weather != null) {
                int enumIndex = -1;
                for (int i = 0; i < Weather.values().length; i++) {
                    if (Weather.values()[i] == weather) {
                        enumIndex = i;
                        break;
                    }
                }
                out.writeByte(enumIndex);
            }
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            temperature = in.readDouble();
            if (in.readByte() == 1) {
                weather = Weather.values()[in.readByte()];
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return isPet == animal.isPet &&
                isWild == animal.isWild &&
                Objects.equals(legsCount, animal.legsCount) &&
                Objects.equals(name, animal.name) &&
                Objects.equals(age, animal.age) &&
                Objects.equals(id, animal.id) &&
                moveType == animal.moveType &&
                Objects.equals(livingEnvironment, animal.livingEnvironment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPet, isWild, legsCount, name, age, id, moveType, livingEnvironment);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "isPet=" + isPet +
                ", isWild=" + isWild +
                ", legsCount=" + legsCount +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", moveType=" + moveType +
                ", id=" + id +
                ", livingEnvironment=" + livingEnvironment +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeByte(isPet ? 1 : 0);
        out.writeByte(isWild ? 1 : 0);
        out.writeByte(assertNullGetBit(legsCount));
        if (legsCount != null) {
            out.writeByte(legsCount);
        }
        out.writeByte(assertNullGetBit(name));
        if (name != null) {
            out.writeUTF(name);
        }
        out.writeByte(assertNullGetBit(age));
        if (age != null) {
            out.writeDouble(age);
        }
        out.writeByte(assertNullGetBit(moveType));
        if (moveType != null) {
            int enumIndex = -1;
            for (int i = 0; i < MoveType.values().length; i++) {
                if (MoveType.values()[i] == moveType) {
                    enumIndex = i;
                    break;
                }
            }
            out.writeByte(enumIndex);
        }
        out.writeByte(assertNullGetBit(id));
        if (id != null) {
            out.writeByte(id);
        }
        out.writeByte(assertNullGetBit(livingEnvironment));
        if (livingEnvironment != null) {
            out.writeObject(livingEnvironment);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        isPet = in.readByte()  > 0;
        isWild = in.readByte()  > 0;
        if (in.readByte()  > 0) {
            legsCount = (int) in.readByte();
        }
        if (in.readByte()  > 0) {
            name = in.readUTF();
        }
        if (in.readByte()  > 0) {
            age = in.readDouble();
        }
        if (in.readByte()  > 0) {
            moveType = MoveType.values()[in.readByte()];
        }
        if (in.readByte()  > 0) {
            id = in.readByte();
        }
        if (in.readByte()  > 0) {
            livingEnvironment = (LivingEnvironment) in.readObject();
        }
    }

    private static int assertNullGetBit(Object object) {
        return object == null ? 0 : 1;
    }
}
