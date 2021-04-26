package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooFewWordsAnalyzer implements TextAnalyzer {

    private final int minWords;

    public TooFewWordsAnalyzer(int min) {
        minWords = min;
    }

    @Override
    public boolean check(String text) {

        if (minWords == 0) {
            return false;
        } else if (text == null) {
            return true;
        }

        String[] words = text.split("[^\\p{L}&']+");
        // регулярное выражение ловит промежутки между словами (& понимается как слово "and")

        return words.length < minWords;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_FEW_WORDS;
    }
}
