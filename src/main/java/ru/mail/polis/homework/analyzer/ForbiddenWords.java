package ru.mail.polis.homework.analyzer;

public class ForbiddenWords implements TextAnalyzer {
    protected String[] forbiddenWords;
    protected FilterType filterType;

    @Override
    public boolean analyze(String text) {
        for (String word : forbiddenWords) {
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
