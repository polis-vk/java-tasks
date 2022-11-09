package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Worker implements Serializable {
    private String surname;
    private long id;
    private boolean isOnVacation;

    public Worker(String surname, long id, boolean isOnVacation) {
        this.surname = surname;
        this.id = id;
        this.isOnVacation = isOnVacation;
    }

    public String getSurname() {
        return surname;
    }

    public long getId() {
        return id;
    }

    public boolean isOnVacation() {
        return isOnVacation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return Objects.equals(surname, worker.surname) && id == worker.id && isOnVacation == worker.isOnVacation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, id, isOnVacation);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "surname='" + surname + '\'' +
                ", id=" + id +
                ", isOnVacation=" + isOnVacation +
                '}';
    }
}
