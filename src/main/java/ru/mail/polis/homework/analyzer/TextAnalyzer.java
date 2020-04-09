package ru.mail.polis.homework.analyzer;

public interface TextAnalyzer {
    FilterType analyze(String txt);

    FilterType getFilterType();

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new LongAnalyzer(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamAnalyzer(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextAnalyzer();
    }

    static TextAnalyzer createCustomAnalyzer() {
        return new CustomAnalyzer();
    }
}


