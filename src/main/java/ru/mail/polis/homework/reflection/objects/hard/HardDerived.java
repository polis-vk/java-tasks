package ru.mail.polis.homework.reflection.objects.hard;

import ru.mail.polis.homework.reflection.SkipField;

public class HardDerived extends HardBase {
    public static final String DEFAULT_NAME = "John Doe";
    public static final String[] DEFAULT_NICKNAMES = new String[] {};
    public static final double DEFAULT_WEIGHT = 65.0;

    private final String name;
    private final String[] nicknames;

    @SkipField
    private final double weight;

    public HardDerived() {
        this(DEFAULT_AGE, DEFAULT_NAME, DEFAULT_NICKNAMES, DEFAULT_WEIGHT);
    }

    public HardDerived(int age, String name, String[] nicknames, double weight) {
        super(age);
        this.name = name;
        this.nicknames = nicknames;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String[] getNicknames() {
        return nicknames;
    }
}
