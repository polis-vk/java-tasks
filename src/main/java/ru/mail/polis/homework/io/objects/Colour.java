package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Colour implements Serializable {
    private int mainColor;
    private boolean stains;
    private boolean stripes;

    public Colour(int m, boolean sa, boolean st) {
        this.mainColor = m;
        this.stains = sa;
        this.stripes = st;
    }

    public int getMainColor() {
        return mainColor;
    }

    public boolean getStains() {
        return stains;
    }

    public boolean getStripes() {
        return stripes;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Colour that = (Colour) obj;
        return this.mainColor == that.mainColor
                && this.stains == that.stains
                && this.stripes == that.stripes;
    }

    @Override
    public String toString() {
        return "Colour{" +
                "main color='" + mainColor + '\'' +
                ", stains=" + stains + '\'' +
                ", stripes=" + stripes +
                '}';
    }

}
