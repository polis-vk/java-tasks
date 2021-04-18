package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {
    private final byte priority = 4;
    private final String[] formatirovanie = { " ,", " .", " !", " ?" }; /* можно ещё что-то добавить */

    public boolean check(String str) {
        for (int i = 0; i < formatirovanie.length; i++) {
            if (str.contains(formatirovanie[i])) {
                return true;
            }
        }
        return false;
    }

    public FilterType filter() {
        return FilterType.CUSTOM;
    }

    public byte priority() {
        return priority;
    }

}
