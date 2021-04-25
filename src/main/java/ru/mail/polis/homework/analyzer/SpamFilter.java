package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] SPAM;
    private static final FilterType typeOfFilter = FilterType.SPAM;

    public SpamFilter(String[] spam) {
        this.SPAM = spam;
    }

    public FilterType getType() {
        return typeOfFilter;
    }

    @Override
    public boolean analyze(String text) {
        if (TextAnalyzer.analyzeCoidences(SPAM, text)) {
            return true;
        }
        return false;
    }
}
