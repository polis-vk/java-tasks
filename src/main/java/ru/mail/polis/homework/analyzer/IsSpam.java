package ru.mail.polis.homework.analyzer;

public class IsSpam extends StringAnalyzer implements TextAnalyzer {

    private final String[] SPAM_TEXT;
    public IsSpam(String[] spam) {
        SPAM_TEXT = spam;
    }

    @Override
    public FilterType analyze(String arg) {
        return stringAnalyzer(arg, SPAM_TEXT, FilterType.SPAM);
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.SPAM;
    }
}
