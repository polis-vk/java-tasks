package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Зоопарк, в котором находится животное
 */
public class Zoo implements Serializable {
    private int id;
    private String name;

    public Zoo() {

    }

    public Zoo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zoo zoo = (Zoo) o;
        return id == zoo.id && Objects.equals(name, zoo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
