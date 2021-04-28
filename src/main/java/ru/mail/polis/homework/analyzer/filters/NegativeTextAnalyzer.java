package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextAnalyzer extends WordChecker {

    static public final String[] negativeWords = new String[] {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(negativeWords);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}