package ru.mail.polis.homework.analyzer;

public class CapsLockAnalyzer implements TextAnalyzer {
    public static final double ALLOW_PERSNT_OF_CAPS = 0.5;

    public FilterType check(String txt) {
        char[] txtArray = txt.toCharArray();
        int counter = 0;
        for (char symbol : txtArray) {
            if (Character.isLetter(symbol) && Character.isUpperCase(symbol)) {
                counter++;
            }
        }
        if (counter >= txtArray.length * ALLOW_PERSNT_OF_CAPS) {
            return FilterType.CAPS;
        }
        return null;
    }
}