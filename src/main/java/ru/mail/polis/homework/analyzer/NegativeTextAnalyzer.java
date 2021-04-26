package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends StringArrayAnalyzer {
    NegativeTextAnalyzer() {
        keys = new String[]{"=(", ":(", ":|"};
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
