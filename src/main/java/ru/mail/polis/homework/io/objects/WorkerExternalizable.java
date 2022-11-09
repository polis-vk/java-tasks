package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class WorkerExternalizable implements Externalizable {
    private String surname;
    private long id;
    private boolean isOnVacation;

    public WorkerExternalizable(String surname, long id, boolean isOnVacation) {
        this.surname = surname;
        this.id = id;
        this.isOnVacation = isOnVacation;
    }

    public WorkerExternalizable() {
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
        WorkerExternalizable worker = (WorkerExternalizable) o;
        return Objects.equals(surname, worker.surname) && id == worker.id && isOnVacation == worker.isOnVacation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, id, isOnVacation);
    }

    @Override
    public String toString() {
        return "WorkerExternalizable{" +
                "surname='" + surname + '\'' +
                ", id=" + id +
                ", isOnVacation=" + isOnVacation +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (surname != null) {
            out.writeByte(1);
            out.writeUTF(surname);
        } else {
            out.writeByte(0);
        }
        out.writeLong(id);
        out.writeBoolean(isOnVacation);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readByte() == 1) {
            surname = in.readUTF();
        }
        id = in.readLong();
        isOnVacation = in.readBoolean();
    }
}
