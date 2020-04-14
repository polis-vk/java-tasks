package ru.mail.polis.homework.analyzer;

public class SpamTextFilter extends Analyzing implements TextAnalyzer {

    private final String[] SPAM;

    public SpamTextFilter(String[] Spam) {
        this.SPAM = Spam;
    }

    @Override
    public FilterType analyze(String text) {
        return analyzing(text, SPAM, FilterType.SPAM);
    }
}
