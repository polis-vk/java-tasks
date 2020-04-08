package ru.mail.polis.homework.analyzer;


import ru.mail.polis.homework.analyzer.filters.CorrectCaseFilter;
import ru.mail.polis.homework.analyzer.filters.NegativeTextFilter;
import ru.mail.polis.homework.analyzer.filters.SpamFilter;
import ru.mail.polis.homework.analyzer.filters.TooLongFilter;

public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new TooLongFilter(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamFilter(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextFilter();
    }

    static TextAnalyzer createCustomAnalyzer() {
        return new CorrectCaseFilter();
    }

    FilterType analyze(String text);

    FilterType getType();
}
