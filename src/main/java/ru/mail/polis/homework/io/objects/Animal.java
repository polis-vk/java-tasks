package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String alias;
    private int legs;
    private boolean wild;
    private boolean furry;
    private Organization organization;
    private MoveType moveType;

    public String getAlias() {
        return alias;
    }

    public int getLegs() {
        return legs;
    }

    public boolean isWild() {
        return wild;
    }

    public boolean isFurry() {
        return furry;
    }

    public Organization getOrganization() {
        return organization;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setWild(boolean wild) {
        this.wild = wild;
    }

    public void setFurry(boolean furry) {
        this.furry = furry;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return legs == animal.legs && wild == animal.wild && furry == animal.furry
                && Objects.equals(alias, animal.alias) && Objects.equals(organization, animal.organization)
                && moveType == animal.moveType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, legs, wild, furry, organization, moveType);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "alias='" + alias + '\'' +
                ", legs=" + legs +
                ", wild=" + wild +
                ", furry=" + furry +
                ", organization=" + organization +
                ", moveType=" + moveType +
                '}';
    }

    static class Organization implements Serializable {
        private String name;
        private String owner;
        private boolean foreign;

        public String getName() {
            return name;
        }

        public String getOwner() {
            return owner;
        }

        public boolean isForeign() {
            return foreign;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setForeign(boolean foreign) {
            this.foreign = foreign;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Organization organization = (Organization) o;
            return foreign == organization.foreign && Objects.equals(name, organization.name)
                    && Objects.equals(owner, organization.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, owner, foreign);
        }

        @Override
        public String toString() {
            return "Organization{" +
                    "name='" + name + '\'' +
                    ", owner='" + owner + '\'' +
                    ", foreign=" + foreign +
                    '}';
        }
    }
}

