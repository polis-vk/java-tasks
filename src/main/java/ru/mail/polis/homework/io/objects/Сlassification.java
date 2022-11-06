package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Классификация животных
 */
public class Сlassification implements Serializable {

    private String type;
    private String family;

    public Сlassification(String type, String family) {
        this.type = type;
        this.family = family;
    }

    public Сlassification() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Сlassification classification = (Сlassification) o;
        return type.equals(classification.type) &&
                family.equals(classification.family);
    }

    public int hashCode() {
        return Objects.hash(type, family);
    }

    public String toString() {
        return "Classification{" +
                "type = " + type +
                ", family = " + family +
                '}' + '\n';
    }

}
