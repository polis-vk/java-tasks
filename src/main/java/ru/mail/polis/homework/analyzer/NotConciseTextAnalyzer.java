package ru.mail.polis.homework.analyzer;

public class NotConciseTextAnalyzer extends ContentChecker implements TextAnalyzer {
    private static final FilterType type = FilterType.NOT_CONCISE_TEXT;
    private final static String[] parasiticWords = {"В общем,", "Короче,", "Значит,"};

    @Override
    public FilterType filtering(String text) {
        return textContainWord(text, parasiticWords) ? FilterType.NOT_CONCISE_TEXT : FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return type;
    }
}
