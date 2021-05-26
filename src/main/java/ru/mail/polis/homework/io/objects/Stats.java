package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Stats implements Serializable {
    private double hp;
    private double damage;
    private double energy;

    public Stats(double hp, double damage, double energy) {
        this.hp = hp;
        this.damage = damage;
        this.energy = energy;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeDouble(hp);
        outputStream.writeDouble(damage);
        outputStream.writeDouble(energy);
    }

    public static Stats deserialize(DataInputStream inputStream) throws IOException {
        return new Stats(inputStream.readDouble(), inputStream.readDouble(), inputStream.readDouble());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Double.compare(stats.hp, hp) == 0 && Double.compare(stats.damage, damage) == 0 && Double.compare(stats.energy, energy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, damage, energy);
    }

    @Override
    public String toString() {
        return "Stats{" +
                "hp=" + hp +
                ", damage=" + damage +
                ", energy=" + energy +
                '}';
    }
}
