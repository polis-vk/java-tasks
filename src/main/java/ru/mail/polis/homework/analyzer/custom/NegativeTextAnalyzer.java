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
    public boolean haveProblem(String text) {
        for (String negativeString : negativeStrings) {
            if (text.contains(negativeString)) {
                return true;
            }
        }
        return false;
    }
}
