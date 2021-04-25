package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null) {
            return false;
        }

        for (int i = 0; i < spam.length; i++) {
            if (text.contains(spam[i])) {
                return true;
            }
        }
        return false;
    }
}
