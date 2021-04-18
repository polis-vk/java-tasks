package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negativeItems = new String[]{"=(", ":(", ":|"};

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public FilterType analyze(String text) {
        for (String negativeItem : negativeItems) {
            if (text.contains(negativeItem)) {
                return filterType();
            }
        }
        return FilterType.GOOD;
    }
}
