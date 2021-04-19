package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    final int PRIORITY = 1;
    private final String[] spam;
    private static final FilterType typeOfFilter = FilterType.SPAM;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    public FilterType getType() {
        return typeOfFilter;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        for (String badWord : spam) {
            if (text.contains(badWord)) {
                return true;
            }
        }
        return false;
    }
}
