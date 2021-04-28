package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.TextAnalyzer;
import ru.mail.polis.homework.analyzer.FilterType;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final String[] EVADE_WORDS;
    private static final int PRIORITY = 0;

    public SpamFilter(String[] EvadeWordsParam) {
        this.EVADE_WORDS = Arrays.copyOf(EvadeWordsParam, EvadeWordsParam.length);
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : this.EVADE_WORDS) {
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
    public int getPriority() {
        return PRIORITY;
    }

}
