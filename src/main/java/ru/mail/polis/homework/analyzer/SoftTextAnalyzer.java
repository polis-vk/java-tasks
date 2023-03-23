package ru.mail.polis.homework.analyzer;

public class SoftTextAnalyzer extends SpamTextAnalyzer implements TextAnalyzer {
    public SoftTextAnalyzer() {
        super(new String[]{"милый", "добрый", "котик", "зайка", "красивый"});
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SOFT_TEXT;
    }

    @Override
    public boolean check(String text) {
        return super.check(text);
    }
}
