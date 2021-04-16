package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private static final FilterType type = FilterType.SPAM;

    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        for (String spamWord : spam) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
