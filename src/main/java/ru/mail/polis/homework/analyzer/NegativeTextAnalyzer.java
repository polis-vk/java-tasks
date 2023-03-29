package ru.mail.polis.homework.analyzer;

import java.util.regex.Pattern;

public class NegativeTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType getFilterType(String str) {
        String regex = "(=\\(|:\\(|:\\|)";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find() ? FilterType.NEGATIVE_TEXT : FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
