package ru.mail.polis.homework.analyzer;

public class SpamFilter extends NegativeTextFilter implements TextAnalyzer {
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean analysis(String text) {
        return textContain(text, spam);
    }
}
