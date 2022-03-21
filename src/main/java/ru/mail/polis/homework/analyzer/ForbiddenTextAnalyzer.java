package ru.mail.polis.homework.analyzer;

public class ForbiddenTextAnalyzer implements TextAnalyzer {
    protected String[] forbiddenText;
    protected FilterType filterType;

    @Override
    public boolean analyze(String text) {
        for (String word : forbiddenText) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
