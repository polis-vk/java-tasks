package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] NEGATIVE_EMOTIONS = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        boolean result = true;
        for (String negative_emotion: NEGATIVE_EMOTIONS){
            if (text.contains(negative_emotion)){
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
