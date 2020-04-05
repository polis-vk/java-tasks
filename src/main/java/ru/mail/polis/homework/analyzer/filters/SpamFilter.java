package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    private static final short VALUE = 0;

    private String[] args;

    public SpamFilter(String[] spam) {
        args = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType analyze(String str) {
        for (String arg : args) {
            if (str.contains(arg)) {
                return FilterType.SPAM;
            }
        }
        return null;
    }

    @Override
    public short getValue() {
        return VALUE;
    }
}
