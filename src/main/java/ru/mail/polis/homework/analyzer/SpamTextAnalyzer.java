package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

import static ru.mail.polis.homework.analyzer.FilterType.GOOD;
import static ru.mail.polis.homework.analyzer.FilterType.SPAM;

public class SpamTextAnalyzer implements TextAnalyzer {

    private final String[] badWords;
    private final byte priority = 0;

    public SpamTextAnalyzer(String[] badWords) {
        this.badWords = badWords;
    }

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        boolean anyMatch = Arrays.stream(badWords).anyMatch(text::contains);
        return anyMatch ? SPAM : GOOD;
    }

}

// test push 3
