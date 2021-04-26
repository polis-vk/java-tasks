package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class SpamAnalyzer extends WordChecker {

    public SpamAnalyzer(String[] words) {
        super(words);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
