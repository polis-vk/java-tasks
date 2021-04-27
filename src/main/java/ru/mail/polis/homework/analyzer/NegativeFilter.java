package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final FilterType TYPEOFFILTER = FilterType.NEGATIVE_TEXT;
    private static final String[] NEGATIVEWORDS = {"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return TYPEOFFILTER;
    }

    @Override
    public boolean analyze(String text) {
        if (TextAnalyzer.analyzeCoidences(NEGATIVEWORDS, text)) {
            return true;
        }
        return false;
    }
}
