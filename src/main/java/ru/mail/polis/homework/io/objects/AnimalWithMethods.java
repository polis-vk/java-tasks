package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private AnimalGroup group;
    private String name;
    private boolean isWarmBlooded;
    private Behavior behavior;
    private List<HabitatEnvironment> habitatEnvironments;

    // default value of -1, if unknown
    private int age;

    // RGBA format
    // default value of 0x00000000 (black color with alpha == 0.0), if unknown
    private int color;

    // default value of Double.NaN, if unknown
    private double weight;

    private AnimalWithMethods(Builder builder, Behavior behavior) {
        this.group = builder.group;
        this.name = builder.name;
        this.isWarmBlooded = builder.isWarmBlooded;
        this.behavior = behavior;
        this.habitatEnvironments = Collections.unmodifiableList(builder.habitatEnvironments);

        this.age = builder.age;
        this.color = builder.color;
        this.weight = builder.weight;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(group);
        oos.writeObject(name);
        oos.writeBoolean(isWarmBlooded);
        oos.writeObject(behavior);

        oos.writeInt(habitatEnvironments.size());
        for (HabitatEnvironment environment : habitatEnvironments) {
            oos.writeObject(environment);
        }
        oos.writeInt(age);
        oos.writeInt(color);
        oos.writeDouble(weight);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        group = (AnimalGroup) ois.readObject();
        name = (String) ois.readObject();
        isWarmBlooded = ois.readBoolean();
        behavior = (Behavior) ois.readObject();

        int habitatEnvironmentsCount = ois.readInt();
        habitatEnvironments = new ArrayList<>();
        for (int i = 0; i < habitatEnvironmentsCount; i++) {
            habitatEnvironments.add((HabitatEnvironment) ois.readObject());
        }

        age = ois.readInt();
        color = ois.readInt();
        weight = ois.readDouble();
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
        return String.format("AnimalWithMethods { Group: %s, Name: %s, Type: %s, Habitat environments: %s, "
                + "Behavior: \"%s\", Age: %s years, Color: %s, Weight: %s kg }",
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

        AnimalWithMethods other = (AnimalWithMethods) otherObject;
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
        private boolean canBeTamed;
        private boolean isPredator;
        private AnimalMovementType movementType;
        private List<String> enemies, friends;
        private List<String> favouriteFood;

        private Behavior(boolean canBeTamed, boolean isPredator, AnimalMovementType movementType, List<String> favouriteFood,
                         List<String> enemies, List<String> friends) {
            this.canBeTamed = canBeTamed;
            this.isPredator = isPredator;
            this.movementType = movementType;

            this.favouriteFood = favouriteFood;
            this.enemies = enemies;
            this.friends = friends;
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            oos.writeBoolean(canBeTamed);
            oos.writeBoolean(isPredator);
            oos.writeObject(movementType);

            oos.writeInt(enemies.size());
            for (String enemy : enemies) {
                oos.writeObject(enemy);
            }

            oos.writeInt(friends.size());
            for (String friend : friends) {
                oos.writeObject(friend);
            }

            oos.writeInt(favouriteFood.size());
            for (String favouriteFoodItem : favouriteFood) {
                oos.writeObject(favouriteFoodItem);
            }
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            canBeTamed = ois.readBoolean();
            isPredator = ois.readBoolean();
            movementType = (AnimalMovementType) ois.readObject();

            int enemiesCount = ois.readInt();
            enemies = new ArrayList<>();
            for (int i = 0; i < enemiesCount; i++) {
                enemies.add((String) ois.readObject());
            }
            enemies = Collections.unmodifiableList(enemies);

            int friendsCount = ois.readInt();
            friends = new ArrayList<>();
            for (int i = 0; i < friendsCount; i++) {
                friends.add((String) ois.readObject());
            }
            friends = Collections.unmodifiableList(friends);

            int favouriteFoodItemsCount = ois.readInt();
            favouriteFood = new ArrayList<>();
            for (int i = 0; i < favouriteFoodItemsCount; i++) {
                favouriteFood.add((String) ois.readObject());
            }
            favouriteFood = Collections.unmodifiableList(favouriteFood);
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

        public AnimalWithMethods build() {
            Behavior behavior = new Behavior(canBeTamed, isPredator, movementType, favouriteFood, enemies, friends);
            return new AnimalWithMethods(this, behavior);
        }
    }

    public static void main(String[] args) {
        AnimalWithMethods dog = new AnimalWithMethods.Builder(AnimalGroup.MAMMALS, "Pug", true, true, false, AnimalMovementType.WALKING)
                .withAge(2)
                .withWeight(4.9)
                .withHabitatEnvironments(HabitatEnvironment.HOME)
                .withFriends("Human")
                .withEnemies("Cat")
                .withFavouriteFood("Bones")
                .build();

        AnimalWithMethods cat = new AnimalWithMethods.Builder(AnimalGroup.MAMMALS, "Siamese cat", true, true, false, AnimalMovementType.WALKING)
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
