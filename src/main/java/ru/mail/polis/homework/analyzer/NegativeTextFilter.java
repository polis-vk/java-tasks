package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private final static FilterType typeFilter = FilterType.NEGATIVE_TEXT;
    private final static String[] negativeWords = {"=(", ":(", ":|"};

    @Override
    public FilterType getFilterType() {
        return typeFilter;
    }

    @Override
    public FilterType analyze(String str) {
        return FindInText.find(negativeWords, str, typeFilter);
    }

}
