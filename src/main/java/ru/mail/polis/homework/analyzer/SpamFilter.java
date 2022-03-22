package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] badWords;

    public SpamFilter(String[] badWords) {
        this.badWords = badWords;
    }

    @Override
    public boolean analyze(String text) {
        for (String s : badWords) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
