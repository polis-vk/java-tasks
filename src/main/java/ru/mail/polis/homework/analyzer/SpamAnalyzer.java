package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;
    private final FilterType type = FilterType.SPAM;

    SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public boolean checkTextIsCorrect(String text) {
        return TextAnalyzer.checkInclusion(text, spam);
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
