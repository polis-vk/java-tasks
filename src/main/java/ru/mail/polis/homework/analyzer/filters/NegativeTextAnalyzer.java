package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    @Override
    public FilterType getResult(String text) {
        SpamAnalyzer spamAnalyzer = new SpamAnalyzer(NEGATIVE_TEXT);
        FilterType result = spamAnalyzer.getResult(text);
        if (result == FilterType.SPAM) {
            return FilterType.NEGATIVE_TEXT;
        }
        return result;
    }

    @Override
    public int getIdResult() {
        return 3;
    }
}
