package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class TooFewWordsAnalyzer extends Filter {

    private int minWords;

    @Override
    public boolean check(String text) {

        if (minWords == 0) {
            return false;
        } else if (text == null) {
            return true;
        }

        String[] words = text.split("[^\\p{L}&']+");
        // регулярное выражение ловит промежутки между словами (& понимается как слово "and")

        if (words.length < minWords) {
            return true;
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_FEW_WORDS;
    }

    public TooFewWordsAnalyzer(int min) {
        minWords = min;
        order = 4;
    }
}
