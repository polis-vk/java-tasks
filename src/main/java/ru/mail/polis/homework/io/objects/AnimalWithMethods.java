package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods {
    public enum Diet {
        PREDATOR,
        SCAVENGER,
        HERBIVORE,
        OMNIVORE
    }

    private String name;
    private final Diet diet;
    private final Genotype genotype;
    private final int speciesId;
    private final List<Integer> scaredOf;
    private final boolean singleCell;

    public AnimalWithMethods(String name,
                  Diet diet,
                  Genotype genotype,
                  int speciesId,
                  List<Integer> scaredOf,
                  boolean singleCell) {
        this.name = name;
        this.diet = diet;
        this.genotype = genotype;
        this.speciesId = speciesId;
        this.scaredOf = new ArrayList<>(scaredOf);
        this.singleCell = singleCell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public Diet getDiet() {
        return diet;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public List<Integer> getScaredOf() {
        return Collections.unmodifiableList(scaredOf);
    }

    public void scareOf(int speciesId) {
        scaredOf.add(speciesId);
    }

    public void notScareOf(int speciesId) {
        scaredOf.remove(Integer.valueOf(speciesId));
    }

    public boolean isSingleCell() {
        return singleCell;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", diet=" + diet +
                ", genotype=" + genotype +
                ", speciesId=" + speciesId +
                ", scaredOf=" + scaredOf +
                ", singleCell=" + singleCell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return speciesId == that.speciesId &&
                singleCell == that.singleCell &&
                Objects.equals(name, that.name) &&
                diet == that.diet &&
                Objects.equals(genotype, that.genotype) &&
                Objects.equals(scaredOf, that.scaredOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diet, genotype, speciesId, scaredOf, singleCell);
    }

    public void write(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeObject(diet);
        out.writeObject(genotype);
        out.writeInt(speciesId);
        out.writeInt(scaredOf.size());
        for (Integer integer : scaredOf) {
            out.writeInt(integer);
        }
        out.writeBoolean(singleCell);
    }

    public static AnimalWithMethods read(ObjectInput in) throws IOException, ClassNotFoundException {
        String name = in.readUTF();
        Diet diet = (Diet) in.readObject();
        Genotype genotype = (Genotype) in.readObject();
        int speciesId = in.readInt();
        int size = in.readInt();
        List<Integer> scared = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scared.add(in.readInt());
        }
        boolean singleCell = in.readBoolean();
        return new AnimalWithMethods(name, diet, genotype, speciesId, scared, singleCell);
    }
}
