package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private int legs;
    private boolean isDomesticated;
    private boolean isWarmBlooded;
    private String name;
    private Diet diet;
    private PersonExternalizable discoveredBy;

    public AnimalExternalizable(int legs,
                  boolean isDomesticated,
                  boolean isWarmBlooded,
                  String name,
                  Diet diet,
                  PersonExternalizable discoveredBy) {
        this.legs = legs;
        this.isDomesticated = isDomesticated;
        this.isWarmBlooded = isWarmBlooded;
        this.name = name;
        this.diet = diet;
        this.discoveredBy = discoveredBy;
    }

    public AnimalExternalizable() {
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public boolean isDomesticated() {
        return isDomesticated;
    }

    public void setDomesticated(boolean domesticated) {
        isDomesticated = domesticated;
    }

    public boolean isWarmBlooded() {
        return isWarmBlooded;
    }

    public void setWarmBlooded(boolean warmBlooded) {
        isWarmBlooded = warmBlooded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public PersonExternalizable getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(PersonExternalizable discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return legs == that.legs &&
                isDomesticated == that.isDomesticated &&
                isWarmBlooded == that.isWarmBlooded &&
                name.equals(that.name) &&
                diet == that.diet &&
                Objects.equals(discoveredBy, that.discoveredBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, isDomesticated, isWarmBlooded, name, diet, discoveredBy);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "legs=" + legs +
                ", isDomesticated=" + isDomesticated +
                ", isWarmBlooded=" + isWarmBlooded +
                ", name='" + name + '\'' +
                ", diet=" + diet +
                ", discoveredBy=" + discoveredBy +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(getFlag());
        out.writeInt(legs);
        out.writeBoolean(isDomesticated);
        out.writeBoolean(isWarmBlooded);
        out.writeUTF(name);
        out.writeObject(diet);
        if (discoveredBy != null) {
            out.writeObject(discoveredBy);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        boolean isDiscovered = in.readBoolean();
        legs = in.readInt();
        isDomesticated = in.readBoolean();
        isWarmBlooded = in.readBoolean();
        name = in.readUTF();
        diet = (Diet) in.readObject();
        if (isDiscovered) {
            discoveredBy = (PersonExternalizable) in.readObject();
        } else {
            discoveredBy = null;
        }
    }

    private boolean getFlag() {
        return discoveredBy != null;
    }
}

class PersonExternalizable implements Externalizable {
    private String name;
    private String shortBio;
    private LocalDate born;
    private LocalDate died;

    public PersonExternalizable(String name, String shortBio, LocalDate born, LocalDate died) {
        this.name = name;
        this.shortBio = shortBio;
        this.born = born;
        this.died = died;
    }

    public PersonExternalizable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public LocalDate getDied() {
        return died;
    }

    public void setDied(LocalDate died) {
        this.died = died;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonExternalizable person = (PersonExternalizable) o;
        return name.equals(person.name) &&
                shortBio.equals(person.shortBio) &&
                born.equals(person.born) &&
                Objects.equals(died, person.died);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortBio, born, died);
    }

    @Override
    public String toString() {
        return "PersonExternalizable{" +
                "name='" + name + '\'' +
                ", shortBio='" + shortBio + '\'' +
                ", born=" + born +
                ", died=" + died +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(getFlag());
        out.writeUTF(name);
        out.writeUTF(shortBio);
        out.writeObject(born);
        if (died != null) {
            out.writeObject(died);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        boolean isDied = in.readBoolean();
        name = in.readUTF();
        shortBio = in.readUTF();
        born = (LocalDate) in.readObject();
        if (isDied) {
            died = (LocalDate) in.readObject();
        } else {
            died = null;
        }
    }

    private boolean getFlag() {
        return died != null;
    }
}
