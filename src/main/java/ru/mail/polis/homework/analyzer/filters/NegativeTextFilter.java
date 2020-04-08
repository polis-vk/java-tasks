package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextFilter implements TextAnalyzer {
    private final FilterType negativeFilterType = FilterType.NEGATIVE_TEXT;
    private final String[] negativeLiterals = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String text) {
        for (String spamLiteral: negativeLiterals) {
            if (text.contains(spamLiteral)) {
                return negativeFilterType;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return negativeFilterType;
    }
}
