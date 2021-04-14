package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public byte getPriority() {
        return 1;
    }

    @Override
    public boolean isValid(String text) {
        for (String s : spam) {
            if (text.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
