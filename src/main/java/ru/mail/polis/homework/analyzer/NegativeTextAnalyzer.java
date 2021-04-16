package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private byte priority = 3;

    public boolean check(String str) {
        char c;
        for (int i = 1; i < str.length(); i++) {
            c = str.charAt(i);
            switch (c) {
            case ('('):
                if (str.charAt(i - 1) == ':' || str.charAt(i - 1) == '=') {
                    return true;
                }
            case ('|'):
                if (str.charAt(i - 1) == ':') {
                    return true;
                }
            }
        }
        return false;
    }

    public FilterType filter() {
        return FilterType.NEGATIVE_TEXT;
    }

    public byte priority() {
        return priority;
    }
}
