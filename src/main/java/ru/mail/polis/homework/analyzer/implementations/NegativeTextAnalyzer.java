package ru.mail.polis.homework.analyzer.implementations;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextAnalyzer extends KeywordAnalyzer implements TextAnalyzer {

    private final FilterType filterType = FilterType.NEGATIVE_TEXT;

    public NegativeTextAnalyzer() {
        this.keywords = new String[] {"=(", ":(", ":|"};
    }

    @Override
    public FilterType getLabel() {
        return filterType;
    }
}
