package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends Analyzer implements TextAnalyzer {
    private static final String[] smileExample = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String txt) {
        if (analyzeText(txt, smileExample)) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
