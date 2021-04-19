package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.SPAM;

    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean analyze(String text) {
        return text != null && !text.isEmpty() && NegativeTextAnalyzer.contains(text, spam);
    }
}
