package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

    private AnimalKind animalKind;
    private String name = "unknown";
    private int age;
    private int weight;
    private List<String> locationsList = new ArrayList<>();
    private Colour colour = Colour.UNKNOWN;

    AnimalExternalizable() {
    }

    public static AnimalExternalizable getRandom() {
        return newBuilder().getRandom();
    }

    public static Builder newBuilder() {
        return new AnimalExternalizable().new Builder();
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(animalKind);
        out.writeUTF(name);
        out.writeInt(age);
        out.writeInt(weight);
        out.writeObject(locationsList);
        out.writeObject(colour);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        animalKind = (AnimalKind) in.readObject();
        name = in.readUTF();
        age = in.readInt();
        weight = in.readInt();
        if (age > 100)
            throw new IllegalArgumentException();
        locationsList = (List<String>) in.readObject();
        colour = (Colour) in.readObject();
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "animalKind=" + animalKind +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", locationsList=" + locationsList +
                ", colour=" + colour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age &&
                weight == that.weight &&
                animalKind == that.animalKind &&
                Objects.equals(name, that.name) &&
                Objects.equals(locationsList, that.locationsList) &&
                colour == that.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalKind, name, age, weight, locationsList, colour);
    }

    public class Builder {

        private AnimalExternalizable getRandom() {
            setAnimalKind(AnimalKind.getRandom(Utils.random));
            setName(Utils.getRandomString(Utils.random, 10));
            setAge(Utils.random.nextInt(99));
            setWeight(Utils.random.nextInt(500));
            for (int i = 0; i < Utils.random.nextInt(20); i++)
                addLocations(Utils.getRandomString(Utils.random, 12));
            setColour(Colour.getRandom(Utils.random));
            return build();
        }

        private Builder() {
        }

        public Builder setAnimalKind(AnimalKind animalKind) {
            AnimalExternalizable.this.animalKind = animalKind;
            return this;
        }

        public Builder setName(String name) {
            AnimalExternalizable.this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            AnimalExternalizable.this.age = age;
            return this;
        }

        public Builder setWeight(int weight) {
            AnimalExternalizable.this.weight = weight;
            return this;
        }

        public Builder addLocations(String... locations) {
            AnimalExternalizable.this.locationsList.addAll(Arrays.asList(locations));
            return this;
        }

        public Builder setLocationList(List<String> locationList) {
            AnimalExternalizable.this.locationsList = locationList;
            return this;
        }

        public Builder setColour(Colour colour) {
            AnimalExternalizable.this.colour = colour;
            return this;
        }

        public AnimalExternalizable build() {
            return AnimalExternalizable.this;
        }
    }
}
