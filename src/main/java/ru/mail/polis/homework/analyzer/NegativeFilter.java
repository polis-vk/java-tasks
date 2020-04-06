package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {

    public static long priority;

    public NegativeFilter() {

        super(new String[]{"=(", ":(", ":|"});
        this.type = FilterType.NEGATIVE_TEXT;


    }

    @Override
    public long getPriority() {
        return priority;
    }
}
