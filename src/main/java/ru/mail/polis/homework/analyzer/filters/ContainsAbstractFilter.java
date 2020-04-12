package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

abstract public class ContainsAbstractFilter implements TextAnalyzer {

    public FilterType analyze(String[] containsText, String text) {
        for (String spamElement : containsText) {
            if (text.contains(spamElement)) {
                return null;
            }
        }
        return FilterType.GOOD;
    }

}
