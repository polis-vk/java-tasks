package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] badWords;

    public SpamFilter(String[] spam) {
        badWords = spam;
    }

    @Override
    public boolean filterText(String text) {
        for (String s : badWords) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
