package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final int priority = 3;
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;
    private static final String[] badSmiles = {"=(", ":(", ":|"};


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String commentary) {
        for (String str : badSmiles) {
            if (commentary.contains(str)) {
                return false;
            }
        }
        return true;
    }
}
