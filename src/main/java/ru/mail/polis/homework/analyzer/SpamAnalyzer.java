package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends StringArrayAnalyzer {
    public SpamAnalyzer(String[] spam) {
        this.keys = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
