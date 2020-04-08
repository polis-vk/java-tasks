package ru.mail.polis.homework.analyzer;

public class CapsLockAnalyzer implements TextAnalyzer {
    private static final double ALLOW_PERSNT_OF_CAPS = 0.5;

    public boolean check(String txt) {
        char[] txtArray = txt.toCharArray();
        int counter = 0;
        for (char symbol : txtArray) {
            if (Character.isLetter(symbol) && Character.isUpperCase(symbol)) {
                counter++;
            }
        }
        if (counter >= txtArray.length * ALLOW_PERSNT_OF_CAPS) {
            return true;
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.CAPS;
    }
}