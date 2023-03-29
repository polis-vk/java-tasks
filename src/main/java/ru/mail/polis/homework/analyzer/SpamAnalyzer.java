package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer extends ContentChecker implements TextAnalyzer {
    private static final FilterType type = FilterType.SPAM;
    private final String[] spamMessages;

    public SpamAnalyzer(String[] spamMessages) {
            this.spamMessages = spamMessages;
    }

    @Override
    public FilterType filtering(String text) {
        return textContainWord(text, spamMessages) ? FilterType.SPAM : FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return type;
    }
}
