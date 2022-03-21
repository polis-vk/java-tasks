package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final int priority;
    private final String[] negativeSmiles = new String[] {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        this.priority = FilterType.NEGATIVE_TEXT.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }

        for (String negative : negativeSmiles) {
            if (text.contains(negative)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }
}
