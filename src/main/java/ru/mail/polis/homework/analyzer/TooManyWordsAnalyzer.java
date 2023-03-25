package ru.mail.polis.homework.analyzer;

public class TooManyWordsAnalyzer implements TextAnalyzer {
    int maxWords;

    TooManyWordsAnalyzer(int maxWords) {
        this.maxWords = maxWords;
    }

    @Override
    public FilterType analyzeText(String text) {
        FilterType resultType = FilterType.GOOD;
        if (text == null) {
            return resultType;
        }
        if (text.split(" +").length > maxWords) {
            resultType = FilterType.TOO_MANY_WORDS;
        }
        return resultType;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_MANY_WORDS;
    }
}
