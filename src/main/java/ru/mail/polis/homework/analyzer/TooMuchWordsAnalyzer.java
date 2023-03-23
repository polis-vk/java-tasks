package ru.mail.polis.homework.analyzer;

public class TooMuchWordsAnalyzer implements TextAnalyzer {
    private final long wordCounter;

    TooMuchWordsAnalyzer(long wordCounter) {
        this.wordCounter = wordCounter;
    }

    @Override
    public int getTypeOrder() {
        return FilterType.TOO_MUSH_WORDS.order;
    }

    @Override
    public FilterType analyze(String text) {
        return text.isEmpty() ? FilterType.GOOD : text.split(" ").length > wordCounter ? FilterType.TOO_MUSH_WORDS : FilterType.GOOD;
    }
}
