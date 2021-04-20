package ru.mail.polis.homework.analyzer.implementations;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public abstract class KeywordAnalyzer implements TextAnalyzer {

    protected String[] keywords = null;

    @Override
    public FilterType process(String text) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return getLabel();
            }
        }
        return FilterType.GOOD;
    }
}
