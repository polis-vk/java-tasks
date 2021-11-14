package ru.mail.polis.homework.reflection.objects.hard;

import ru.mail.polis.homework.reflection.ReflectionToStringHelper;
import ru.mail.polis.homework.reflection.SkipField;
import ru.mail.polis.homework.reflection.objects.medium.Medium;

public class Hard extends HardDerived {
    private final Medium bestFriend;
    private final Gender gender;
    @SkipField
    private int healthPoints;

    public Hard(int age, String name, String[] nicknames, double weight, Medium bestFriend, Gender gender, int healthPoints) {
        super(age, name, nicknames, weight);
        this.bestFriend = bestFriend;
        this.gender = gender;
        this.healthPoints = healthPoints;
    }

    public Medium getBestFriend() {
        return bestFriend;
    }

    public Gender getGender() {
        return gender;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    @Override
    public String toString() {
        return ReflectionToStringHelper.reflectiveToString(this);
    }
}
