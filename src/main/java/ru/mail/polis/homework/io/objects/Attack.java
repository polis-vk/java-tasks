package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Attack implements Serializable {
    private final String name;
    private final int damage;

    public Attack(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, damage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Attack))
            return false;
        Attack attack = (Attack) o;
        return Objects.equals(name, attack.name) && damage == attack.damage;
    }
}
