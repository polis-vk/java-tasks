package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

import static ru.mail.polis.homework.analyzer.FilterType.*;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeText = new String[]{ "=(", ":(", ":|"};
    private final byte priority = 2;

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        boolean anyMatch = Arrays.stream(negativeText).anyMatch(text::contains);
        return anyMatch ? NEGATIVE_TEXT : GOOD;
    }
}
