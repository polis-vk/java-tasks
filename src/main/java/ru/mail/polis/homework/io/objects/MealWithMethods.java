package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealWithMethods implements Serializable {

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    private String favouriteMeal;
    private LocalDateTime lastMeal;
    private int mealsPerDay;

    public MealWithMethods(String favouriteMeal, LocalDateTime lastMeal, int mealsPerDay) {
        this.favouriteMeal = favouriteMeal;
        this.lastMeal = lastMeal;
        this.mealsPerDay = mealsPerDay;
    }

    @Override
    public String toString() {
        return "MealWithMethods{" +
                "favouriteMeal='" + favouriteMeal + '\'' +
                ", lastMeal=" + lastMeal +
                ", mealsPerDay=" + mealsPerDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MealWithMethods that = (MealWithMethods) o;
        return mealsPerDay == that.mealsPerDay && Objects.equals(favouriteMeal, that.favouriteMeal)
                && Objects.equals(lastMeal, that.lastMeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favouriteMeal, lastMeal, mealsPerDay);
    }

    private void writeObject(ObjectOutputStream out) {
        try {
            writeUTF(out, favouriteMeal);
            writeUTF(out, lastMeal.toString());
            out.writeInt(mealsPerDay);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readObject(ObjectInputStream in) {
        try {
            favouriteMeal = readUTF(in);
            String lastMealString = readUTF(in);
            lastMeal = null;
            if (lastMealString != null) {
                lastMeal = LocalDateTime.parse(lastMealString);
            }
            mealsPerDay = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUTF(ObjectOutputStream out, String string) throws IOException {
        if (string == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeUTF(string);
        }
    }

    private String readUTF(ObjectInputStream in) throws IOException {
        return in.readByte() == 1 ? in.readUTF() : null;
    }
}
