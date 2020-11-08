package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AnimalInfoWithMethods implements Serializable {

    private int averageAge;
    private String inhabitancy;
    private boolean lonely;

    public AnimalInfoWithMethods(int averageAge, String inhabitancy, boolean lonely) {
        this.averageAge = averageAge;
        this.inhabitancy = inhabitancy;
        this.lonely = lonely;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(averageAge);
        out.writeUTF(inhabitancy);
        out.writeBoolean(lonely);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        averageAge = in.readInt();
        inhabitancy = in.readUTF();
        lonely = in.readBoolean();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalInfoWithMethods that = (AnimalInfoWithMethods) o;
        return averageAge == that.getAverageAge() &&
                lonely == that.isLonely() &&
                inhabitancy.equals(that.getInhabitancy());
    }

    @Override
    public String toString() {
        return "AnimalInfo{" +
                "averageAge=" + averageAge +
                ", inhabitancy='" + inhabitancy + '\'' +
                ", lonely=" + lonely +
                '}';
    }

    public int getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(int averageAge) {
        this.averageAge = averageAge;
    }

    public String getInhabitancy() {
        return inhabitancy;
    }

    public void setInhabitancy(String inhabitancy) {
        this.inhabitancy = inhabitancy;
    }

    public boolean isLonely() {
        return lonely;
    }

    public void setLonely(boolean lonely) {
        this.lonely = lonely;
    }
}
