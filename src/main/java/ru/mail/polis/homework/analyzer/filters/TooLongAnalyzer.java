package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }


    @Override
    public FilterType getResult(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getIdResult() {
        return 2;
    }
}
