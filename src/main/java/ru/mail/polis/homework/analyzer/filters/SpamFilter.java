package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final static int rank = 1;
    private final String[] avoidWords;

    public SpamFilter(String[] avoidWordsParam) {
        this.avoidWords = Arrays.copyOf(avoidWordsParam, avoidWordsParam.length);
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : this.avoidWords) {
            if (text.contains(word)) {
                return filterType();
            }
        }
        return FilterType.GOOD;
    }

    public FilterType filterType() {
        return FilterType.SPAM;
    }

    @Override
    public int getRank() {
        return rank;
    }
}
