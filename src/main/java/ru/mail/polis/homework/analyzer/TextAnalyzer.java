package ru.mail.polis.homework.analyzer;

public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new TooLongAnalyzer(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamAnalyzer(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextAnalyzer();
    }

    static TextAnalyzer createLongSpaceAnalyzer() {
        return new LongSpaceAnalyzer();
    }

    boolean check(String text);

    FilterType getFilterType();
}
