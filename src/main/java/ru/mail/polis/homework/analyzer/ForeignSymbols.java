package ru.mail.polis.homework.analyzer;

public class ForeignSymbols implements TextAnalyzer {

    private static final char[] CHECK = {'a', 'c', 'e', 'y', 'o', 'p', 'x'};

    @Override
    public FilterType typeOfType(String text) {
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < 7; j++) {
                if (text.charAt(i) == CHECK[j]) {
                    return FilterType.CUSTOM;
                }
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int priority() {
        return FilterType.CUSTOM.ordinal();
    }
}
