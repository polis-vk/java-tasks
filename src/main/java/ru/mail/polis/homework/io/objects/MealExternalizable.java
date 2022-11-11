package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealExternalizable implements Externalizable {

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    private String favouriteMeal;
    private LocalDateTime lastMeal;
    private int mealsPerDay;

    public MealExternalizable(String favouriteMeal, LocalDateTime lastMeal, int mealsPerDay) {
        this.favouriteMeal = favouriteMeal;
        this.lastMeal = lastMeal;
        this.mealsPerDay = mealsPerDay;
    }

    public MealExternalizable() {
    }

    @Override
    public String toString() {
        return "MealExternalizable{" +
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
        MealExternalizable that = (MealExternalizable) o;
        return mealsPerDay == that.mealsPerDay && Objects.equals(favouriteMeal, that.favouriteMeal)
                && Objects.equals(lastMeal, that.lastMeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favouriteMeal, lastMeal, mealsPerDay);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeUTF(out, favouriteMeal);
        writeUTF(out, lastMeal.toString());
        out.writeInt(mealsPerDay);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        favouriteMeal = readUTF(in);
        String lastMealString = readUTF(in);
        lastMeal = null;
        if (lastMealString != null) {
            lastMeal = LocalDateTime.parse(lastMealString);
        }
        mealsPerDay = in.readInt();
    }

    private void writeUTF(ObjectOutput out, String string) throws IOException {
        if (string == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeUTF(string);
        }
    }

    private String readUTF(ObjectInput in) throws IOException {
        return in.readByte() == 1 ? in.readUTF() : null;
    }
}
