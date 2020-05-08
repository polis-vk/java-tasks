package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Date;

public class Vaccination implements Serializable {
    private final String date;
    private final String type;
    private final String clinic;
    private final String doctor;

    public Vaccination(String date, String type, String clinic, String doctor) {
        this.date = date;
        this.type = type;
        this.clinic = clinic;
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getClinic() {
        return clinic;
    }

    public String getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "Vaccination: " +
                "date - " + date +
                ", type - " + type +
                ", clinic - '" + clinic + '\'' +
                ", DR. - " + doctor;
    }
}
