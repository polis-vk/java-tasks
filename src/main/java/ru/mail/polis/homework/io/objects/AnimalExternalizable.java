package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.*;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    private AnimalGroup group;
    private String name;
    private boolean isWarmBlooded;
    private Behavior behavior;
    private List<HabitatEnvironment> habitatEnvironments;

    // default value of -1, if unknown
    private int age = -1;

    // RGBA format
    // default value of 0x00000000 (black color with alpha == 0.0), if unknown
    private int color = 0x00000000;

    // default value of Double.NaN, if unknown
    private double weight = Double.NaN;

    public AnimalExternalizable() {
        habitatEnvironments = new ArrayList<>();
    }

    private AnimalExternalizable(Builder builder, Behavior behavior) {
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

        AnimalExternalizable other = (AnimalExternalizable) otherObject;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(group.ordinal());
        out.writeUTF(name);
        out.writeBoolean(isWarmBlooded);
        out.writeObject(behavior);

        out.writeInt(habitatEnvironments.size());
        for (HabitatEnvironment environment : habitatEnvironments) {
            out.writeInt(environment.ordinal());
        }
        out.writeInt(age);
        out.writeInt(color);
        out.writeDouble(weight);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        group = AnimalGroup.values()[in.readInt()];
        name = in.readUTF();
        isWarmBlooded = in.readBoolean();
        behavior = (Behavior) in.readObject();

        int habitatEnvironmentsCount = in.readInt();
        for (int i = 0; i < habitatEnvironmentsCount; i++) {
            habitatEnvironments.add(HabitatEnvironment.values()[in.readInt()]);
        }
        habitatEnvironments = Collections.unmodifiableList(habitatEnvironments);

        age = in.readInt();
        color = in.readInt();
        weight = in.readDouble();
    }

    public static class Behavior implements Externalizable {
        private boolean canBeTamed;
        private boolean isPredator;
        private AnimalMovementType movementType;
        private List<String> enemies, friends;
        private List<String> favouriteFood;

        public Behavior() {
            enemies = new ArrayList<>();
            friends = new ArrayList<>();
            favouriteFood = new ArrayList<>();
        }

        private Behavior(boolean canBeTamed, boolean isPredator, AnimalMovementType movementType, List<String> favouriteFood,
                         List<String> enemies, List<String> friends) {
            this.canBeTamed = canBeTamed;
            this.isPredator = isPredator;
            this.movementType = movementType;

            this.favouriteFood = favouriteFood;
            this.enemies = enemies;
            this.friends = friends;
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

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeBoolean(canBeTamed);
            out.writeBoolean(isPredator);
            out.writeInt(movementType.ordinal());

            out.writeInt(enemies.size());
            for (String enemy : enemies) {
                out.writeUTF(enemy);
            }

            out.writeInt(friends.size());
            for (String friend : friends) {
                out.writeUTF(friend);
            }

            out.writeInt(favouriteFood.size());
            for (String favouriteFoodItem : favouriteFood) {
                out.writeUTF(favouriteFoodItem);
            }
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            canBeTamed = in.readBoolean();
            isPredator = in.readBoolean();
            movementType = AnimalMovementType.values()[in.readInt()];

            int enemiesCount = in.readInt();
            for (int i = 0; i < enemiesCount; i++) {
                enemies.add(in.readUTF());
            }
            enemies = Collections.unmodifiableList(enemies);

            int friendsCount = in.readInt();
            for (int i = 0; i < friendsCount; i++) {
                friends.add(in.readUTF());
            }
            friends = Collections.unmodifiableList(friends);

            int favouriteFoodItemsCount = in.readInt();
            for (int i = 0; i < favouriteFoodItemsCount; i++) {
                favouriteFood.add(in.readUTF());
            }
            favouriteFood = Collections.unmodifiableList(favouriteFood);
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

        public AnimalExternalizable build() {
            Behavior behavior = new Behavior(canBeTamed, isPredator, movementType, favouriteFood, enemies, friends);
            return new AnimalExternalizable(this, behavior);
        }
    }

    public static void main(String[] args) {
        AnimalExternalizable dog = new AnimalExternalizable.Builder(AnimalGroup.MAMMALS, "Pug", true, true, false, AnimalMovementType.WALKING)
                .withAge(2)
                .withWeight(4.9)
                .withHabitatEnvironments(HabitatEnvironment.HOME)
                .withFriends("Human")
                .withEnemies("Cat")
                .withFavouriteFood("Bones")
                .build();

        AnimalExternalizable cat = new AnimalExternalizable.Builder(AnimalGroup.MAMMALS, "Siamese cat", true, true, false, AnimalMovementType.WALKING)
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

