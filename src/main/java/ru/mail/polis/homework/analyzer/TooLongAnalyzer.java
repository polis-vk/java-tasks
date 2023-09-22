package ru.mail.polis.homework.analyzer;

import org.w3c.dom.Text;

public class TooLongAnalyzer implements TextAnalyzer {
    private long size;
    TooLongAnalyzer(long words)
    {
        this.size = words;
    }
    @Override
    public FilterType getFilter() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean textAnalyzer(String text) {
        return text.length() <= size;
    }
}
