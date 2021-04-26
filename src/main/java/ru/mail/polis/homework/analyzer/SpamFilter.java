package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType filterType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean analyzeText(String text) {
        return TextFilterManager.analyzeText(text, spam);
    }
}
