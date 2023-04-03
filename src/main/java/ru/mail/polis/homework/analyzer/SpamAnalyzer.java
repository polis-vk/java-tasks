package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer, CheckWord {

    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    public boolean analyzer(String text) {
        return checkWord(text, spam);
    }

    public FilterType getType() {
        return FilterType.SPAM;
    }
}
