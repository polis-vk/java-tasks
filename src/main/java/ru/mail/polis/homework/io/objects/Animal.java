package ru.mail.polis.homework.io.objects;

import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal {
    private final Group group;
    private final String name;
    private final boolean isWarmBlooded;
    private final Behavior behavior;
    private final List<HabitatEnvironment> habitatEnvironments;

    // default value of -1, if unknown
    private int age;
    // default value of 0x00000000, if unknown
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

    @Override
    public String toString() {
        return String.format("Animal { Group: %s, Name: %s, Type: %s, Habitat environments: %s, Behavior: \"%s\", "
                + "Age: %s, Color: %s, Weight: %s }",
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
        return isWarmBlooded == other.isWarmBlooded &&
                age == other.age &&
                color == other.color &&
                Double.compare(other.weight, weight) == 0 &&
                group == other.group &&
                Objects.equals(name, other.name) &&
                Objects.equals(behavior, other.behavior) &&
                Objects.equals(habitatEnvironments, other.habitatEnvironments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, name, isWarmBlooded, behavior, habitatEnvironments, age, color, weight);
    }

    public enum HabitatEnvironment {
        FRESH_WATER, OCEAN, SWAMP, DESERT, JUNGLE, TAIGA, MOUNTAINS, PLAINS, HOME
    }

    public enum Group {
        MAMMALS, BIRDS, FISH, REPTILES, AMPHIBIANS, INSECTS
    }

    public static class Behavior {
        private final boolean canBeTamed;
        private final boolean isPredator;
        private final MovementType movementType;
        private final List<String> enemies, friends;
        private final List<String> favouriteFood;

        private Behavior(boolean canBeTamed, boolean isPredator, MovementType movementType, List<String> favouriteFood,
                         List<String> enemies, List<String> friends) {
            this.canBeTamed = canBeTamed;
            this.isPredator = isPredator;
            this.movementType = movementType;

            this.favouriteFood = favouriteFood;
            this.enemies = enemies;
            this.friends = friends;
        }

        public enum MovementType {
            FLYING, WALKING, SWIMMING, JUMPING;

            @Override
            public String toString() {
                switch (this) {
                    case FLYING:
                        return "flies";
                    case WALKING:
                        return "walks";
                    case SWIMMING:
                        return "swims";
                    case JUMPING:
                        return "jumps";
                }

                return "does something weird";
            }
        }

        @Override
        public String toString() {
            return String.format(
                    "Acts as a %s and %s, %s around and likes to eat %s. Befriends with %s. Hates %s.",
                    isPredator ? "predator" : "peaceful animal",
                    canBeTamed ? "can be tamed" : "cannot be tamed",
                    movementType,
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
            return canBeTamed == other.canBeTamed &&
                    isPredator == other.isPredator &&
                    movementType == other.movementType &&
                    Objects.equals(enemies, other.enemies) &&
                    Objects.equals(friends, other.friends) &&
                    Objects.equals(favouriteFood, other.favouriteFood);
        }

        @Override
        public int hashCode() {
            return Objects.hash(canBeTamed, isPredator, movementType, enemies, friends, favouriteFood);
        }
    }

    public static class Builder {
        private final Group group;
        private final String name;
        private final boolean isWarmBlooded;
        private final boolean canBeTamed;
        private final boolean isPredator;
        private final Behavior.MovementType movementType;

        private final List<HabitatEnvironment> habitatEnvironments;
        private final List<String> enemies, friends;
        private final List<String> favouriteFood;

        private int age = -1;
        private int color = 0;
        private double weight = Double.NaN;

        public Builder(Group group, String name, boolean isWarmBlooded, boolean canBeTamed, boolean isPredator,
                        Behavior.MovementType movementType) {
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

    public static void main(String[] args) {
        Animal dog = new Animal.Builder(Group.MAMMALS, "Pug", true, true, false, Behavior.MovementType.WALKING)
                .withAge(2)
                .withWeight(4.9)
                .withHabitatEnvironments(HabitatEnvironment.HOME)
                .withFriends("Human")
                .withEnemies("Cat")
                .withFavouriteFood("Bones")
                .build();

        Animal cat = new Animal.Builder(Group.MAMMALS, "Siamese cat", true, true, false, Behavior.MovementType.WALKING)
                .withColor(0xC6CDC7FF)
                .withWeight(3.8)
                .withHabitatEnvironments(HabitatEnvironment.HOME)
                .withFriends("Human")
                .withEnemies("Dog")
                .withFavouriteFood("Fish", "Milk")
                .build();

        System.out.println(dog);
        System.out.println(cat);
    }
}
