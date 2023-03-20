package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    public NegativeTextAnalyzer() {

    }

    @Override
    public boolean detected(String expression) {
        return filter.detected(expression);
    }

    @Override
    public int getPriority() {
        return priority_;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType_;
    }

    static SpamAnalyzer filter = new SpamAnalyzer(new String[]{"=(", ":(", ":|"});
    static final int priority_ = 3;
    static final FilterType analyzerType_ = FilterType.NEGATIVE_TEXT;
}
