package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private final AnimalGroup group;
    private final String name;
    private final boolean isWarmBlooded;
    private final Behavior behavior;
    private final List<HabitatEnvironment> habitatEnvironments;

    // default value of -1, if unknown
    private int age;

    // RGBA format
    // default value of 0x00000000 (black color with alpha == 0.0), if unknown
    private int color;

    // default value of Double.NaN, if unknown
    private double weight;

    private Animal(Builder builder, Behavior behavior) {
        this.group = builder.group;
        this.name = builder.name;
        this.isWarmBlooded = builder.isWarmBlooded;
        this.behavior = behavior;
        this.habitatEnvironments = Collections.unmodifiableList(builder.habitatEnvironments);

        this.age = builder.age;
        this.color = builder.color;
        this.weight = builder.weight;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AnimalGroup getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public boolean isWarmBlooded() {
        return isWarmBlooded;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public List<HabitatEnvironment> habitatEnvironments() {
        return habitatEnvironments;
    }

    public int getAge() {
        return age;
    }

    public int getColor() {
        return color;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("Animal { Group: %s, Name: %s, Type: %s, Habitat environments: %s, Behavior: \"%s\", "
                        + "Age: %s years, Color: %s, Weight: %s kg }",
                group, name, isWarmBlooded ? "warm-blooded" : "cold-blooded", habitatEnvironments, behavior,
                age >= 0 ? Integer.toString(age) : "unknown",
                color != 0 ? Integer.toHexString(color) : "unknown",
                !Double.isNaN(weight) ? String.format("%.2f", weight) : "unknown");
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        Animal other = (Animal) otherObject;
        return isWarmBlooded == other.isWarmBlooded
                && age == other.age
                && color == other.color
                && Double.compare(other.weight, weight) == 0
                && group == other.group
                && Objects.equals(name, other.name)
                && Objects.equals(behavior, other.behavior)
                && Objects.equals(habitatEnvironments, other.habitatEnvironments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, name, isWarmBlooded, behavior, habitatEnvironments, age, color, weight);
    }

    public static class Behavior implements Serializable {
        private final boolean canBeTamed;
        private final boolean isPredator;
        private final AnimalMovementType movementType;
        private final List<String> enemies, friends;
        private final List<String> favouriteFood;

        private Behavior(boolean canBeTamed, boolean isPredator, AnimalMovementType movementType, List<String> favouriteFood,
                         List<String> enemies, List<String> friends) {
            this.canBeTamed = canBeTamed;
            this.isPredator = isPredator;
            this.movementType = movementType;

            this.favouriteFood = favouriteFood;
            this.enemies = enemies;
            this.friends = friends;
        }

        public boolean canBeTamed() {
            return canBeTamed;
        }

        public boolean isPredator() {
            return isPredator;
        }

        public AnimalMovementType getMovementType() {
            return movementType;
        }

        public List<String> enemies() {
            return enemies;
        }

        public List<String> friends() {
            return friends;
        }

        public List<String> favouriteFoodList() {
            return favouriteFood;
        }

        @Override
        public String toString() {
            return String.format(
                    "Acts as a %s and %s, %s around and likes to eat %s. Befriends with %s. Hates %s.",
                    isPredator ? "predator" : "peaceful animal",
                    canBeTamed ? "can be tamed" : "cannot be tamed",
                    movementType.getVerb(),
                    favouriteFood.size() > 0 ? favouriteFood : "nothing",
                    friends.size() > 0 ? friends : "nobody",
                    enemies.size() > 0 ? enemies : "nobody");
        }

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || getClass() != otherObject.getClass()) {
                return false;
            }
            Behavior other = (Behavior) otherObject;
            return canBeTamed == other.canBeTamed
                    && isPredator == other.isPredator
                    && movementType == other.movementType
                    && Objects.equals(enemies, other.enemies)
                    && Objects.equals(friends, other.friends)
                    && Objects.equals(favouriteFood, other.favouriteFood);
        }

        @Override
        public int hashCode() {
            return Objects.hash(canBeTamed, isPredator, movementType, enemies, friends, favouriteFood);
        }
    }

    public static class Builder {
        private final AnimalGroup group;
        private final String name;
        private final boolean isWarmBlooded;
        private final boolean canBeTamed;
        private final boolean isPredator;
        private final AnimalMovementType movementType;

        private final List<HabitatEnvironment> habitatEnvironments;
        private final List<String> enemies, friends;
        private final List<String> favouriteFood;

        private int age = -1;
        private int color = 0;
        private double weight = Double.NaN;

        public Builder(AnimalGroup group, String name, boolean isWarmBlooded, boolean canBeTamed, boolean isPredator,
                       AnimalMovementType movementType) {
            this.group = group;
            this.name = name;
            this.isWarmBlooded = isWarmBlooded;
            this.canBeTamed = canBeTamed;
            this.isPredator = isPredator;
            this.movementType = movementType;

            habitatEnvironments = new ArrayList<>();
            enemies = new ArrayList<>();
            friends = new ArrayList<>();
            favouriteFood = new ArrayList<>();
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withColor(int color) {
            this.color = color;
            return this;
        }

        public Builder withWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder withHabitatEnvironments(HabitatEnvironment... habitatEnvironments) {
            this.habitatEnvironments.addAll(Arrays.asList(habitatEnvironments));
            return this;
        }

        public Builder withFriends(String... friends) {
            this.friends.addAll(Arrays.asList(friends));
            return this;
        }

        public Builder withEnemies(String... enemies) {
            this.enemies.addAll(Arrays.asList(enemies));
            return this;
        }

        public Builder withFavouriteFood(String... favouriteFood) {
            this.favouriteFood.addAll(Arrays.asList(favouriteFood));
            return this;
        }

        public Animal build() {
            Behavior behavior = new Behavior(canBeTamed, isPredator, movementType, favouriteFood, enemies, friends);
            return new Animal(this, behavior);
        }
    }
}
