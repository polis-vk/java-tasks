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
    private boolean isPredator;
    private int legs;
    private String color;
    private MoveType moveType;

    private AnimalPassport animalPassport;

    public Animal() {
    }

    public Animal(boolean isPet, boolean isPredator, int legs,
                  String color, MoveType moveType, AnimalPassport animalPassport) {
        this.isPet = isPet;
        this.isPredator = isPredator;
        this.legs = legs;
        this.color = color;
        this.moveType = moveType;
        this.animalPassport = animalPassport;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public AnimalPassport getAnimalPassport() {
        return animalPassport;
    }

    public void setAnimalPassport(AnimalPassport animalPassport) {
        this.animalPassport = animalPassport;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPet(), isPredator(), getLegs(),
                getColor(), getMoveType(), getAnimalPassport());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;
        return isPet() == animal.isPet() &&
                isPredator() == animal.isPredator() &&
                getLegs() == animal.getLegs() &&
                Objects.equals(getColor(), animal.getColor()) &&
                getMoveType() == animal.getMoveType() &&
                Objects.equals(getAnimalPassport(), animal.getAnimalPassport());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "pet='" + isPet() + '\'' +
                ", predator=" + isPredator() +
                ", legs=" + getLegs() +
                ", color=" + getColor() +
                ", moveType=" + getMoveType() +
                ", animalPassport=" + getAnimalPassport() +
                '}';
    }

    static class AnimalPassport {
        private String species;
        private Sex sex;

        private String name;
        private int age;
        private boolean isVaccinated;
        private String descriptionOfAnimal;

        public AnimalPassport() {
        }

        public AnimalPassport(String name, Sex sex, int age, String species, boolean isVaccinated, String descriptionOfAnimal) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.species = species;
            this.isVaccinated = isVaccinated;
            this.descriptionOfAnimal = descriptionOfAnimal;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public Sex getSex() {
            return sex;
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isVaccinated() {
            return isVaccinated;
        }

        public void setVaccinated(boolean vaccinated) {
            isVaccinated = vaccinated;
        }

        public String getDescriptionOfAnimal() {
            return descriptionOfAnimal;
        }

        public void setDescriptionOfAnimal(String descriptionOfAnimal) {
            this.descriptionOfAnimal = descriptionOfAnimal;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSpecies(), getSex(), getName(), getAge(), isVaccinated(), getDescriptionOfAnimal());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            AnimalPassport animalPassport = (AnimalPassport) obj;
            return Objects.equals(getSpecies(), animalPassport.getSpecies()) &&
                    getSex() == animalPassport.getSex() &&
                    Objects.equals(getName(), animalPassport.getName()) &&
                    getAge() == animalPassport.getAge() &&
                    isVaccinated() == animalPassport.isVaccinated() &&
                    Objects.equals(getDescriptionOfAnimal(), animalPassport.getDescriptionOfAnimal());
        }

        @Override
        public String toString() {
            return "AnimalPassport{" +
                    "species='" + getSpecies() + '\'' +
                    ", sex=" + getSex() +
                    ", name=" + getName() +
                    ", age=" + getAge() +
                    ", vaccinated=" + isVaccinated() +
                    ", descriptionOfAnimal=" + getDescriptionOfAnimal() +
                    '}';
        }
    }
}
