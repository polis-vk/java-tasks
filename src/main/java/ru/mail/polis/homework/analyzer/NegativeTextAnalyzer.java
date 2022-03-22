package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends ContainsTextAnalyzer {

    public NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
