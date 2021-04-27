package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] spam;
    private static final FilterType TYPEOFFILTER = FilterType.SPAM;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    public FilterType getType() {
        return TYPEOFFILTER;
    }

    @Override
    public boolean analyze(String text) {
        if (TextAnalyzer.analyzeCoidences(spam, text)) {
            return true;
        }
        return false;
    }
}
