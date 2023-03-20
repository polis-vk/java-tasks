package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    public SpamAnalyzer(String[] spam) {
        spamWords_ = spam;
    }

    @Override
    public boolean detected(String expression) {
        for (String word : spamWords_) {
            if (expression.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPriority() {
        return priority_;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType_;
    }

    String[] spamWords_;
    static final int priority_ = 1;
    static final FilterType analyzerType_ = FilterType.SPAM;
}
