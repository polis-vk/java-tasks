package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class WorkerWithMethods implements Serializable {
    private String surname;
    private long id;
    private boolean isOnVacation;

    public WorkerWithMethods(String surname, long id, boolean isOnVacation) {
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
        WorkerWithMethods worker = (WorkerWithMethods) o;
        return Objects.equals(surname, worker.surname) && id == worker.id && isOnVacation == worker.isOnVacation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, id, isOnVacation);
    }

    @Override
    public String toString() {
        return "WorkerWithMethods{" +
                "surname='" + surname + '\'' +
                ", id=" + id +
                ", isOnVacation=" + isOnVacation +
                '}';
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        if (surname != null) {
            out.writeByte(1);
            out.writeUTF(surname);
        } else {
            out.writeByte(0);
        }
        out.writeLong(id);
        out.writeBoolean(isOnVacation);
    }

    public void readObject(ObjectInputStream in) throws IOException {
        if (in.readByte() == 1) {
            surname = in.readUTF();
        }
        id = in.readLong();
        isOnVacation = in.readBoolean();
    }
}
