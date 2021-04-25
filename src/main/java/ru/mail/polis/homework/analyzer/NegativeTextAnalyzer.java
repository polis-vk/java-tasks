package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer{
    private final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};
    private final int PRIORITY = 3;

    @Override
    public boolean analyze(String str) {
        return TextAnalyzer.contains(this.NEGATIVE_TEXT, str);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public int getPriority() {
        return this.PRIORITY;
    }
}
