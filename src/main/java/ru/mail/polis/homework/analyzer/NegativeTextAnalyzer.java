package ru.mail.polis.homework.analyzer;


public class NegativeTextAnalyzer extends SpamTextAnalyzer implements TextAnalyzer {
    public NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean check(String text) {
        return super.check(text);
    }
}
