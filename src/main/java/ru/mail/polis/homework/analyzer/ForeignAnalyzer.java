package ru.mail.polis.homework.analyzer;

import java.util.logging.Filter;

public class ForeignAnalyzer implements TextAnalyzer {

    private static final Character[] CHECK = {'a', 'c', 'e', 'y', 'o', 'p', 'x'};

    @Override
    public FilterType filterOfType(String text) {
        for (int i = 0; i < CHECK.length; i++) {
            if (text.contains(CHECK[i].toString())) {
                return FilterType.FOREIGN;
            }
        }
        return FilterType.GOOD;
    }

    public FilterType priority() {
        return FilterType.FOREIGN;
    }
}
