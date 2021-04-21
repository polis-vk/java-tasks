package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer extends SpamAnalyzer {
    private final byte priority = 4;
    private static final String[] formatirovanie = { " ,", " .", " !", " ?" }; /* можно ещё что-то добавить */

    public CustomAnalyzer() {
        super(formatirovanie);
    }

    public FilterType filter() {
        return FilterType.CUSTOM;
    }

    public byte priority() {
        return priority;
    }

}
