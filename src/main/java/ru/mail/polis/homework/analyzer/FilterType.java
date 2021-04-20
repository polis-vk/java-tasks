package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM,
    TOO_LONG,
    NEGATIVE_TEXT,
    CUSTOM,
    GOOD;

    public static byte getPriority(TextAnalyzer analyzer) {
        switch (analyzer.getType()) {
            case SPAM:
                return 1;
            case TOO_LONG:
                return 2;
            case NEGATIVE_TEXT:
                return 3;
            case CUSTOM:
                return 4;
            default:
                return 5;
        }
    }
}
