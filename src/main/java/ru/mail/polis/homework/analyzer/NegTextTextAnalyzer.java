package ru.mail.polis.homework.analyzer;

import java.io.File;

public class NegTextTextAnalyzer implements TextAnalyzer {
    String[] negWords = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String str) {
        if (str == null) return FilterType.GOOD;
        for (String spamWord :
                negWords) {
            if (str.contains(spamWord)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
