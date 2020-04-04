package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    @Override
    public FilterType getResult(String text) {
        return SpamAnalyzer.analysisText(text, NEGATIVE_TEXT) ? FilterType.NEGATIVE_TEXT : FilterType.GOOD;
    }

    @Override
    public int getIdResult() {
        return 3;
    }
}
