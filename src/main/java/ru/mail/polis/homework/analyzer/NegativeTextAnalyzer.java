package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BaseFindSomething {

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
