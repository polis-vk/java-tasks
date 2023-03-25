package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.NEGATIVE_TEXT;
    private final String[] negativeStrings = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean analyze(String text) {
        boolean result = false;
        for (int i = 0; i < negativeStrings.length && !result; i++) {
            result = text.contains(negativeStrings[i]);
        }
        return result;
    }
}
