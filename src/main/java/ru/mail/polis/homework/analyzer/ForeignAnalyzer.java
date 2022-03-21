package ru.mail.polis.homework.analyzer;

public class ForeignAnalyzer implements TextAnalyzer {

    private static final Character[] FOREIGN_SYMBOLS = {'a', 'c', 'e', 'y', 'o', 'p', 'x'};

    @Override
    public boolean filterOfType(String text) {
        for (int i = 0; i < FOREIGN_SYMBOLS.length; i++) {
            if (text.contains(FOREIGN_SYMBOLS[i].toString())) {
                return true;
            }
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.FOREIGN;
    }
}
