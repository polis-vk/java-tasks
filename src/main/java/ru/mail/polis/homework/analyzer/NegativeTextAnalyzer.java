package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends Analyzer implements TextAnalyzer {
    NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}

