package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class AnimalInfo implements Serializable {

    private int averageAge;
    private String inhabitancy;
    private boolean lonely;

    public AnimalInfo(int averageAge, String inhabitancy, boolean lonely) {
        this.averageAge = averageAge;
        this.inhabitancy = inhabitancy;
        this.lonely = lonely;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalInfo that = (AnimalInfo) o;
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
