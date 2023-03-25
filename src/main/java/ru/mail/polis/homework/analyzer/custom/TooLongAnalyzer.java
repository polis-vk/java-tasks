package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.TOO_LONG;
    private final long size;

    public TooLongAnalyzer(long size) {
        this.size = size;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean analyze(String text) {
        return text.length() >= size;
    }
}
