package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BaseFindSomething {

    public FilterType type() {
        return FilterType.NEGATIVE_TEXT;
    }
}
