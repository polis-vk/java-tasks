package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Date;

public class Rewards implements Serializable {
    private final int date;
    private final int place;
    private final String nameOfTournament;

    public Rewards(int date, int place, String nameOfTournament) {
        this.date = date;
        this.place = place;
        this.nameOfTournament = nameOfTournament;
    }

    public int getDate() {
        return date;
    }

    public int getPlace() {
        return place;
    }

    public String getNameOfTournament() {
        return nameOfTournament;
    }

    @Override
    public String toString() {
        return "Rewards{" +
                "date=" + date +
                ", place=" + place +
                ", nameOfTournament='" + nameOfTournament + '\'' +
                '}';
    }
}
