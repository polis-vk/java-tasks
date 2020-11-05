package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private List<Color> colors;
    private boolean isTame;
    private EatingStrategy eatingStrategy;
    private Taxonomy taxonomy;

    private AnimalWithMethods(Builder builder, Taxonomy taxonomy) {
        this.name = builder.name;
        this.age = builder.age;
        this.colors = builder.colors;
        this.isTame = builder.isTame;
        this.eatingStrategy = builder.eatingStrategy;
        this.taxonomy = taxonomy;
    }

    @Override
    public String toString() {
        return String.format("Animal { Name: %s, Age: %s, Colors: %s, Tame: %s, Eating Strategy: %s, Taxonomy [%s]}",
                name, age, colors, isTame ? "Yes" : "No", eatingStrategy, taxonomy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return Objects.equals(name, animal.name) &&
                age == animal.age &&
                Objects.equals(colors, animal.colors) &&
                isTame == animal.isTame &&
                eatingStrategy == animal.eatingStrategy &&
                Objects.equals(taxonomy, animal.taxonomy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, colors, isTame, eatingStrategy, taxonomy);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(name);
        oos.writeInt(age);
        oos.writeInt(colors.size());
        for (Color color : colors) {
            oos.writeInt(color.ordinal());
        }
        oos.writeBoolean(isTame);
        oos.writeInt(eatingStrategy.ordinal());
        oos.writeObject(taxonomy);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        name = ois.readUTF();
        age = ois.readInt();
        int colorsCount = ois.readInt();
        for (int i = 0; i < colorsCount; i++) {
            colors.add(Color.values()[ois.readInt()]);
        }
        colors = Collections.unmodifiableList(colors);
        isTame = ois.readBoolean();
        eatingStrategy = EatingStrategy.values()[ois.readInt()];
        taxonomy = (Taxonomy) ois.readObject();
    }

    public static class Taxonomy implements Serializable {
        private String domain;
        private String kingdom;
        private String phylum;
        private String classT;
        private String order;
        private String family;
        private String genus;
        private String species;
        
        private Taxonomy(String domain, String kingdom, String phylum, String classT, String order, String family, String genus, String species) {
            this.domain = domain;
            this.kingdom = kingdom;
            this.phylum = phylum;
            this.classT = classT;
            this.order = order;
            this.family = family;
            this.genus = genus;
            this.species = species;
        }

        public String getDomain() {
            return this.domain;
        }

        public String getKingdom() {
            return this.kingdom;
        }

        public String getPhylum() {
            return this.phylum;
        }

        public String getClassT() {
            return this.classT;
        }

        public String getOrder() {
            return this.order;
        }

        public String getFamily() {
            return this.family;
        }

        public String getGenus() {
            return this.genus;
        }

        public String getSpecies() {
            return this.species;
        }

        @Override
        public String toString() {
            return String.format("Domain: %s, Kingdom: %s, Phylum: %s, Class: %s, Order: %s, Family: %s, Genus: %s, Species: %s",
            domain, kingdom, phylum, classT, order, family, genus, species);
        }

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || getClass() != otherObject.getClass()) {
                return false;
            }
            Taxonomy other = (Taxonomy) otherObject;
            return Objects.equals(domain, other.domain)
                    && Objects.equals(kingdom, other.kingdom)
                    && Objects.equals(phylum, other.phylum)
                    && Objects.equals(classT, other.classT)
                    && Objects.equals(order, other.order)
                    && Objects.equals(family, other.family)
                    && Objects.equals(genus, other.genus)
                    && Objects.equals(species, other.species);
        }

        @Override
        public int hashCode() {
            return Objects.hash(domain, kingdom, phylum, classT, order, family, genus, species);
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            oos.writeUTF(domain);
            oos.writeUTF(kingdom);
            oos.writeUTF(phylum);
            oos.writeUTF(classT);
            oos.writeUTF(order);
            oos.writeUTF(family);
            oos.writeUTF(genus);
            oos.writeUTF(species);
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            domain = ois.readUTF();
            kingdom = ois.readUTF();
            phylum = ois.readUTF();
            classT = ois.readUTF();
            order = ois.readUTF();
            family = ois.readUTF();
            genus = ois.readUTF();
            species = ois.readUTF();
        }
    }

    public static class Builder {
        private String name;
        private int age;
        private final List<Color> colors = new ArrayList<>();
        private boolean isTame;
        private EatingStrategy eatingStrategy;
        private String domain;
        private String kingdom;
        private String phylum;
        private String classT;
        private String order;
        private String family;
        private String genus;
        private String species;

        public Builder(String name, int age, boolean isTame, EatingStrategy eatingStrategy, Color... colors) {
            this.name = name;
            this.age = age;
            this.colors.addAll(Arrays.asList(colors));
            this.isTame = isTame;
            this.eatingStrategy = eatingStrategy;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }        

        public Builder setColors(Color... colors) {
            this.colors.addAll(Arrays.asList(colors));
            return this;
        }

        public Builder setIsTame(boolean isTame) {
            this.isTame = isTame;
            return this;
        }

        public Builder setEatingStrategy(EatingStrategy eatingStrategy) {
            this.eatingStrategy = eatingStrategy;
            return this;
        }

        public Builder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder setKingdom(String kingdom) {
            this.kingdom = kingdom;
            return this;
        }

        public Builder setPhylum(String phylum) {
            this.phylum = phylum;
            return this;
        }

        public Builder setClassT(String classT) {
            this.classT = classT;
            return this;
        }

        public Builder setOrder(String order) {
            this.order = order;
            return this;
        }

        public Builder setFamily(String family) {
            this.family = family;
            return this;
        }

        public Builder setGenus(String genus) {
            this.genus = genus;
            return this;
        }

        public Builder setSpecies(String species) {
            this.species = species;
            return this;
        }
        

        public AnimalWithMethods build() {
            Taxonomy taxonomy = new Taxonomy(domain, kingdom, phylum, classT, order, family, genus, species);
            return new AnimalWithMethods(this, taxonomy);
        }

    }

    public static void main(String[] args) {
        Animal dog = new Animal.Builder("Dog", 0, true, EatingStrategy.OMNIVORE, Color.WHITE)
            .setDomain("Eukaryota")
            .setKingdom("Animalia")
            .setPhylum("Chordata")
            .setClassT("Mammalia")
            .setOrder("Carnivora")
            .setFamily("Canidae")
            .setGenus("Canis")
            .setSpecies("C. lupus")
            .build();

        Animal cat = new Animal.Builder("Cat", 0, true, EatingStrategy.OMNIVORE, Color.BLACK)
            .setDomain("Eukaryota")
            .setKingdom("Animalia")
            .setPhylum("Chordata")
            .setClassT("Mammalia")
            .setOrder("Carnivora")
            .setFamily("Felidae")
            .setGenus("Felis")
            .setSpecies("F. catus")
            .build();

        System.out.println(dog);
        System.out.println(cat);
    }
}
