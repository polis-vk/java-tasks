package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private boolean isPet;
    private boolean isWild;
    private Integer legsCount;
    private String name;
    private Double age;
    private Byte id;
    private MoveType moveType;
    private LivingEnvironment livingEnvironment;

    public Animal() {

    }

    public Animal(boolean isPet, boolean isWild, Integer legsCount, String name, Double age, MoveType moveType,
                  Byte id, LivingEnvironment livingEnvironment) {
        this.isPet = isPet;
        this.isWild = isWild;
        this.legsCount = legsCount;
        this.name = name;
        this.age = age;
        this.moveType = moveType;
        this.id = id;
        this.livingEnvironment = livingEnvironment;
    }

    public boolean isPet() {
        return isPet;
    }

    public boolean isWild() {
        return isWild;
    }

    public Integer getLegsCount() {
        return legsCount;
    }

    public String getName() {
        return name;
    }

    public Double getAge() {
        return age;
    }

    public Byte getId() {
        return id;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public LivingEnvironment getLivingEnvironment() {
        return livingEnvironment;
    }

    public static class LivingEnvironment implements Serializable{
        private final double temperature;
        private final Weather weather;

        public LivingEnvironment(double temperature, Weather weather) {
            this.temperature = temperature;
            this.weather = weather;
        }

        public double getTemperature() {
            return temperature;
        }

        public Weather getWeather() {
            return weather;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
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
        return "Animal{" +
                "isPet=" + isPet +
                ", isWild=" + isWild +
                ", legsCount=" + legsCount +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", moveType=" + moveType +
                ", livingEnvironment=" + livingEnvironment +
                '}';
    }
}
