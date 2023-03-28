package ru.mail.polis.homework.analyzer;

public class BadEmojiFilterAnalyzer implements TextAnalyzer {
    private final String[] emoji = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isFilterPassed(String text) {
        return Finder.find(emoji, text);
    }
}
