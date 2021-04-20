package ru.mail.polis.homework.analyzer.implementations;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class SpamAnalyzer extends KeywordAnalyzer implements TextAnalyzer {

    private final FilterType filterType = FilterType.SPAM;

    public SpamAnalyzer(String[] spam) {
        this.keywords = spam;
    }

    @Override
    public FilterType getLabel() {
        return filterType;
    }
}
