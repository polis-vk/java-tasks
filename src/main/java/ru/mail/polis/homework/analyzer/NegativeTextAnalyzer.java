package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    public static final String[] negativeWords = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String text) {
        for (String negativeWord : negativeWords) {
            if (text.contains(negativeWord)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
