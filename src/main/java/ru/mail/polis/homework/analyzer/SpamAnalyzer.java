package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;
    private final FilterType type = FilterType.SPAM;

    SpamAnalyzer(String[] spam) {
        this.spam = spam.clone();
    }

    @Override
    public boolean analyze(String text) {
        for (String word : spam) {
            if (text.contains(word)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
