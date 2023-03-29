package ru.mail.polis.homework.analyzer;

public class TooMuchWordsAnalyzer implements TextAnalyzer {
    private final long wordCounter;

    TooMuchWordsAnalyzer(long wordCounter) {
        this.wordCounter = wordCounter;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_MUSH_WORDS;
    }

    @Override
    public boolean isFilterFailed(String text) {
        return !text.isEmpty() && text.split(" ").length > wordCounter;
    }
}
