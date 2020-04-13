package ru.mail.polis.homework.analyzer;

public class FilterPriority {
    static int getFilterPriority(TextAnalyzer tn) {
        switch (tn.getFilterAnswer()) {
            case SPAM:
                return 1;
            case TOO_LONG:
                return 2;
            case NEGATIVE_TEXT:
                return 3;
            case NOT_CYRILLIC:
                return 4;
            default:
                return Integer.MAX_VALUE;
        }
    }
}
