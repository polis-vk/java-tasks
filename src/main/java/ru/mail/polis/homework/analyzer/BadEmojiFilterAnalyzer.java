package ru.mail.polis.homework.analyzer;

public class BadEmojiFilterAnalyzer implements TextAnalyzer {
    private final String[] emoji = new String[]{"=(", ":(", ":|"};

    @Override
    public int getTypeOrder() {
        return FilterType.NEGATIVE_TEXT.order;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamWord : emoji) {
            if (text.contains(spamWord)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
