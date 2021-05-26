package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Zoo implements Serializable {

    private String country;

    private String name;

    public Zoo() {

    }

    public Zoo(String country, String name) {
        this.country = country;
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Zoo testObj =(Zoo) obj;
        return testObj.name.equals(name) && testObj.country.equals(country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, name);
    }
}
