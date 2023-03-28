package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] NEGATIVE_EMOTIONS = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        for (String emotion: NEGATIVE_EMOTIONS){
            if (text.contains(emotion)){
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
